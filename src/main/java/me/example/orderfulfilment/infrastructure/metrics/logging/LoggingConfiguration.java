package me.example.orderfulfilment.infrastructure.metrics.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {

    @Bean
    LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
