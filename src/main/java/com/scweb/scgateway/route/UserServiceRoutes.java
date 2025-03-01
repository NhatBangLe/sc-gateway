package com.scweb.scgateway.route;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
@RequiredArgsConstructor
public class UserServiceRoutes {

    @Value("${app.user-service-url}")
    private String userServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> userServiceRouter() {
        return GatewayRouterFunctions
                .route("user-service")
                .route(RequestPredicates.path("/api/v1/user/**"), http(userServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("user-service-swagger")
                .route(RequestPredicates.path("/aggregate/user/v3/api-docs"), http(userServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

}
