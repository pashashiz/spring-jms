package com.ps.tutorial.jms;

import com.ps.tutorial.model.User;
import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private Logger log = Logger.getLogger(getClass());

    @JmsListener(destination = "jms-test-destination", containerFactory = "jmsContainerFactory")
    public void receiveStringMessage(String message) {
        log.debug("Received string [" + message + "]");
    }

    @JmsListener(destination = "jms-object-destination", containerFactory = "jmsContainerFactory")
    public void receiveObjectMessage(User user) {
        log.debug("Received object [" + user + "]");
    }

    @JmsListener(destination = "jms-object-destination", containerFactory = "jmsContainerFactory")
    public void receiveMessage(Message<User> message) {
        log.debug("Received message [" + message.getPayload() + "]");
        log.debug("Message headers [" + message.getHeaders() + "]");
    }

}
