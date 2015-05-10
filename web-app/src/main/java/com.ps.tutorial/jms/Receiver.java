package com.ps.tutorial.jms;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private Logger log = Logger.getLogger(getClass());

    @JmsListener(destination = "jms-test-destination", containerFactory = "jmsContainerFactory")
    public void receiveMessage(String message) {
        log.debug("Received message be receiver [" + message + "]");
    }

}

