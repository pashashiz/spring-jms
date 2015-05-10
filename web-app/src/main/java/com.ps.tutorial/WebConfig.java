package com.ps.tutorial;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
@EnableWebMvc
@EnableJms
@ComponentScan(basePackages = {"com.ps.tutorial.controllers", "com.ps.tutorial.jms"})
public class WebConfig extends WebMvcConfigurationSupport {

    private Logger log = Logger.getLogger(getClass());

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = null;
        try {
            // Use caching connection factory (uses pool of connections)
            connectionFactory = new CachingConnectionFactory(
                    (ConnectionFactory)new InitialContext().lookup("java:comp/env/jms/ConnectionFactory"));
            connectionFactory.setSessionCacheSize(5);
        } catch (NamingException e) {
            log.error("Cannot look up JMS Connection Factory", e);
        }
        return connectionFactory;
    }

    @Bean
    JmsListenerContainerFactory<?> jmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setDefaultDestinationName("jms-test-destination");
        return jmsTemplate;
    }

}
