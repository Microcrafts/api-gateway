package com.microcrafts.gateway;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.CompositeCustomizer;
import io.github.resilience4j.spring6.circuitbreaker.configure.CircuitBreakerConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class CircuitBreakerEventLogger {

    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerEventLogger.class);

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(CircuitBreakerConfigurationProperties properties) {
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();

        properties.getInstances().forEach((name, instanceProperties) -> {
            CircuitBreakerConfig config = properties.createCircuitBreakerConfig(name,instanceProperties, new CompositeCustomizer<>(Collections.emptyList()));
            if (config != null) {
                registry.circuitBreaker(name, config);
            }
        });

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
