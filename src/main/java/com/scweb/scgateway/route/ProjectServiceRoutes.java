package com.scweb.scgateway.route;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
@RequiredArgsConstructor
public class ProjectServiceRoutes {

    @Value("${PROJECT_SERVICE_BASE_URL}")
    private String serviceUrl;
    private final String URL_PREFIX = "/api/v1";

    @Bean
    public RouterFunction<ServerResponse> projectProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("project-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/project/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> stageProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("stage-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/stage/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> formProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("form-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/form/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fieldProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("field-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/field/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> sampleProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("sample-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/sample/**"), HandlerFunctions.http(serviceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> projectServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("project-service-swagger")
                .route(
                        RequestPredicates.path("/aggregate/project/v3/api-docs"),
                        HandlerFunctions.http(serviceUrl)
                )
                .filter(setPath("/api-docs"))
                .build();
    }

}
