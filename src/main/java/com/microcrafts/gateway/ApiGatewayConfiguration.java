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
    public RouterFunction<ServerResponse> routeLocator() {
        return route("movieCatalogRoute").GET(path("/catalog-api/**"), http("http://10.0.0.23:8080"))
                .before(rewritePath("/catalog-api/(?<segment>.*)","/api/${segment}"))
            .route("movieSearchRoute").GET(path("/search-api/**"), http("http://10.0.0.27:8090"))
                .before(rewritePath("/search-api/(?<segment>.*)","/api/${segment}"))
            .build();
    }

}
