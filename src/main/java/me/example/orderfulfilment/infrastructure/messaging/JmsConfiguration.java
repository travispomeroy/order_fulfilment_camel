package me.example.orderfulfilment.infrastructure.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfiguration {

    private final Environment environment;

    public JmsConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        return new ActiveMQConnectionFactory(environment.getProperty("activemq.broker.url"));
    }

    @Bean (initMethod = "start", destroyMethod = "stop")
    public PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setConnectionFactory(jmsConnectionFactory());
        factory.setMaxConnections(Integer.parseInt(environment.getProperty("pooledConnectionFactory.maxConnections")));
        return factory;
    }

    @Bean
    public org.apache.camel.component.jms.JmsConfiguration camelJmsConfiguration() {
        org.apache.camel.component.jms.JmsConfiguration jmsConfiguration =
                new org.apache.camel.component.jms.JmsConfiguration();
        jmsConfiguration.setConnectionFactory(pooledConnectionFactory());
        return jmsConfiguration;
    }

    @Bean
    public ActiveMQComponent activeMQComponent() {
        ActiveMQComponent component = new ActiveMQComponent();
        component.setConfiguration(camelJmsConfiguration());
        return component;
    }
}
