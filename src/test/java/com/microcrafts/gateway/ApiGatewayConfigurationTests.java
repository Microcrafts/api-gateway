package com.microcrafts.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiGatewayConfigurationTests {

    @Autowired
    private ApiGatewayConfiguration apiGatewayConfiguration;

    @Test
    void routeLocatorBeanExists() {
        RouterFunction<ServerResponse> routerFunction = apiGatewayConfiguration.routeLocator();
        assertNotNull(routerFunction, "RouterFunction bean should not be null");
    }
}