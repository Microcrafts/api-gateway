package com.microcrafts.gateway;

import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
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
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("movieCatalogRoute", r -> r
                .path("/catalog-api/**")
                .filters(f -> f.rewritePath("/catalog-api/(?<segment>.*)", "/api/${segment}"))
                .uri("http://10.0.0.23:8080")
            )
            .route("movieSearchRoute", r -> r
                .path("/search-api/**")
                .filters(f -> f.rewritePath("/search-api/(?<segment>.*)", "/api/${segment}"))
                .uri("http://10.0.0.27:8090")
            )
            .build();
    }

}
