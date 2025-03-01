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
public class PostServiceRoutes {

    @Value("${app.post-service-url}")
    private String postServiceUrl;

    private final String URL_PREFIX = "/api";

    @Bean
    public RouterFunction<ServerResponse> commentPostServiceRouter() {
        return GatewayRouterFunctions
                .route("comment-post-service")
                .route(RequestPredicates.path(URL_PREFIX + "/comment/**"), http(postServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> domainPostServiceRouter() {
        return GatewayRouterFunctions
                .route("domain-post-service")
                .route(RequestPredicates.path(URL_PREFIX + "/domain/**"), http(postServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> postPostServiceRouter() {
        return GatewayRouterFunctions
                .route("post-post-service")
                .route(RequestPredicates.path(URL_PREFIX + "/post/**"), http(postServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userPostServiceRouter() {
        return GatewayRouterFunctions
                .route("user-post-service")
                .route(RequestPredicates.path(URL_PREFIX + "/user/**"), http(postServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> postServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("post-service-swagger")
                .route(RequestPredicates.path("/aggregate/post/v3/api-docs"), http(postServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

}
