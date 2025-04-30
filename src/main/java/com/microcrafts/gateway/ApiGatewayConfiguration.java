package com.microcrafts.gateway;

import org.springframework.cloud.gateway.server.mvc.GatewayMvcConfigurer;
import org.springframework.cloud.gateway.server.mvc.GatewayMvcRouteRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration implements GatewayMvcConfigurer {

    @Override
    public void configure(GatewayMvcRouteRegistry registry) {
        registry
            .route("movieCatalogRoute", r -> r
                .path("/catalog-api/**")
                .filters(f -> f.rewritePath("/catalog-api/(?<segment>.*)", "/api/${segment}"))
                .uri("http://10.0.0.23:8080")
            )
            .route("movieSearchRoute", r -> r
                .path("/search-api/**")
                .filters(f -> f.rewritePath("/search-api/(?<segment>.*)", "/api/${segment}"))
                .uri("http://10.0.0.27:8090")
            );
    }
}
