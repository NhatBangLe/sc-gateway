package com.scweb.scgateway.route;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
@RequiredArgsConstructor
public class PostServiceRoutes {

    @Value("${POST_SERVICE_BASE_URL}")
    private String serviceUrl;
    private final String URL_PREFIX = "/api";

    @Bean
    public RouterFunction<ServerResponse> commentPostServiceRouter() {
        return GatewayRouterFunctions
                .route("comment-post-service")
                .route(RequestPredicates.path(URL_PREFIX + "/comment/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> domainPostServiceRouter() {
        return GatewayRouterFunctions
                .route("domain-post-service")
                .route(RequestPredicates.path(URL_PREFIX + "/domain/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> postPostServiceRouter() {
        return GatewayRouterFunctions
                .route("post-post-service")
                .route(RequestPredicates.path(URL_PREFIX + "/post/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userPostServiceRouter() {
        return GatewayRouterFunctions
                .route("user-post-service")
                .route(RequestPredicates.path(URL_PREFIX + "/user/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> postServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("post-service-swagger")
                .route(
                        RequestPredicates.path("/aggregate/post/v3/api-docs"),
                        HandlerFunctions.http(serviceUrl)
                )
                .filter(setPath("/api-docs"))
                .build();
    }

}
