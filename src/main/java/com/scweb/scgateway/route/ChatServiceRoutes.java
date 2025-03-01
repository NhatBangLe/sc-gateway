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
public class ChatServiceRoutes {

    @Value("${app.chat-service-url}")
    private String chatServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> chatServiceRouter() {
        return GatewayRouterFunctions
                .route("chat-service")
                .route(RequestPredicates.path("/api/v1/chat/**"), http(chatServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> chatServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("chat-service-swagger")
                .route(RequestPredicates.path("/aggregate/chat/v3/api-docs"), http(chatServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

}
