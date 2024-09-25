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
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
@RequiredArgsConstructor
public class ChatServiceRoutes {

    @Value("${CHAT_SERVICE_INSTANCE_ID}")
    private String chatServiceId;
    private final String URL_PREFIX = "/api/v1/chat";

    @Bean
    public RouterFunction<ServerResponse> conversationRouter() {
        return GatewayRouterFunctions
                .route("conversation-chat-service")
                .filter(lb(chatServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/conversation/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> attachmentRouter() {
        return GatewayRouterFunctions
                .route("attachment-chat-service")
                .filter(lb(chatServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/attachment/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> messageRouter() {
        return GatewayRouterFunctions
                .route("message-chat-service")
                .filter(lb(chatServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/message/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> participantRouter() {
        return GatewayRouterFunctions
                .route("participant-chat-service")
                .filter(lb(chatServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/participant/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> chatServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("chat-service-swagger")
                .filter(lb(chatServiceId))
                .route(RequestPredicates.path("/aggregate/chat/v3/api-docs"), http())
                .filter(setPath("/api-docs"))
                .build();
    }

}
