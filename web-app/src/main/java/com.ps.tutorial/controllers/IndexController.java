package com.ps.tutorial.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * To test use http://localhost:8080/jms-web-app/send?message=hello
 */
@RestController("/")
public class IndexController {

    private Logger log = Logger.getLogger(getClass());

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    JmsTemplate jmsTemplate;

    @RequestMapping("/send")
    public String send(@RequestParam("message") final String message) throws NamingException {
        log.debug("Received request with message [" + message + "]");
        if (connectionFactory != null) {
            log.debug("Sending a new message...");
            jmsTemplate.send("jms-test-destination",  new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(message);
                }
            });
            return "Message [" + message + "] was sent successfully";
        }
        else {
            return "Cannot send a message, because JMS Connection Factory is not configured";
        }
    }

}
