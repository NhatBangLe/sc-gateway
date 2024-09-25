package com.scweb.scgateway.route;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
@RequiredArgsConstructor
public class ProjectServiceRoutes {

    @Value("${PROJECT_SERVICE_INSTANCE_ID}")
    private String projectServiceId;
    private final String URL_PREFIX = "/api/v1";

    @Bean
    public RouterFunction<ServerResponse> projectProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("project-project-service")
                .filter(lb(projectServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/project/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> stageProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("stage-project-service")
                .filter(lb(projectServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/stage/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> formProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("form-project-service")
                .filter(lb(projectServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/form/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fieldProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("field-project-service")
                .filter(lb(projectServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/field/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> sampleProjectServiceRouter() {
        return GatewayRouterFunctions
                .route("sample-project-service")
                .filter(lb(projectServiceId))
                .route(RequestPredicates.path(URL_PREFIX + "/sample/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> projectServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("project-service-swagger")
                .filter(lb(projectServiceId))
                .route(RequestPredicates.path("/aggregate/project/v3/api-docs"), http())
                .filter(setPath("/api-docs"))
                .build();
    }

}
