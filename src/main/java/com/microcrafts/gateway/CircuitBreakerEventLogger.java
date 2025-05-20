package com.microcrafts.gateway;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircuitBreakerEventLogger {

    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerEventLogger.class);

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();

        registry.getAllCircuitBreakers().forEach(this::registerEventLogger);

        return registry;
    }

    private void registerEventLogger(CircuitBreaker circuitBreaker) {
        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> logger.info("CircuitBreaker '{}' state changed to {}",
                        event.getCircuitBreakerName(),
                        event.getStateTransition()))
                .onError(event -> logger.error("CircuitBreaker '{}' recorded an error: {}",
                        event.getCircuitBreakerName(),
                        event.getThrowable().getMessage()))
                .onSuccess(event -> logger.info("CircuitBreaker '{}' recorded a success",
                        event.getCircuitBreakerName()));
    }
}
