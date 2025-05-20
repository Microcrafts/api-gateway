package com.microcrafts.gateway;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routeLocator() {
        return route()
                .GET(path("/catalog-api/**"), http("http://10.0.0.23:8080")).before(rewritePath("/catalog-api/(?<segment>.*)", "/api/${segment}"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("catalogApiCircuitBreaker"))
                .POST(path("/catalog-api/**"), http("http://10.0.0.23:8080")).before(rewritePath("/catalog-api/(?<segment>.*)", "/api/${segment}"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("catalogApiCircuitBreaker"))
                .PUT(path("/catalog-api/**"), http("http://10.0.0.23:8080")).before(rewritePath("/catalog-api/(?<segment>.*)", "/api/${segment}"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("catalogApiCircuitBreaker"))
                .DELETE(path("/catalog-api/**"), http("http://10.0.0.23:8080")).before(rewritePath("/catalog-api/(?<segment>.*)", "/api/${segment}"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("catalogApiCircuitBreaker"))
                .POST(path("/search-api/**"), http("http://10.0.0.30:8090")).before(rewritePath("/search-api/(?<segment>.*)", "/api/${segment}"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("searchApiCircuitBreaker"))
                .GET(path("/search-api/**"), http("http://10.0.0.30:8090")).before(rewritePath("/search-api/(?<segment>.*)", "/api/${segment}"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("searchApiCircuitBreaker"))
                .build();
    }
}
