package com.ps.tutorial;

import com.ps.tutorial.model.User;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class Runner {

    private static Logger log = Logger.getLogger(Runner.class);

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        // Send a message
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("Hello");
            }
        };
        JmsTemplate jmsTemplate = ctx.getBean(JmsTemplate.class);
        log.debug("Sending 5 test messages...");
        for (int i = 0; i < 5; i++) {
            jmsTemplate.send("jms-test-destination", messageCreator);
            Thread.sleep(1000);
        }
        log.debug("Sending message with object value...");
        jmsTemplate.send("jms-object-destination", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(new User("Pavlo", "Pohrebnyi"));
            }
        });
        Thread.sleep(1000);
        log.debug("Close application");
        ctx.close();
    }

}
