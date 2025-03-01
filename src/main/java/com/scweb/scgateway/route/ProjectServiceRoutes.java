package com.scweb.scgateway.route;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
@RequiredArgsConstructor
public class ProjectServiceRoutes {

    @Value("${app.project-service-url}")
    private String projectServiceUrl;

    private final String URL_PREFIX = "/api/v1";

    @Bean
    public RouterFunction<ServerResponse> projectProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("project-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/project/**"), http(projectServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> stageProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("stage-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/stage/**"), http(projectServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> formProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("form-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/form/**"), http(projectServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fieldProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("field-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/field/**"), http(projectServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> sampleProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("sample-project-service")
                .route(RequestPredicates.path(URL_PREFIX + "/sample/**"), http(projectServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> projectServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("project-service-swagger")
                .route(RequestPredicates.path("/aggregate/project/v3/api-docs"), http(projectServiceUrl))
                .filter(setPath("/api-docs"))
                .build();
    }

}
