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
public class UserServiceRoutes {

    @Value("${USER_SERVICE_INSTANCE_ID}")
    private String userServiceId;

    @Bean
    public RouterFunction<ServerResponse> userServiceRouter() {
        return GatewayRouterFunctions
                .route("user-service")
                .filter(lb(userServiceId))
                .route(RequestPredicates.path("/api/v1/user/**"), http())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("user-service-swagger")
                .filter(lb(userServiceId))
                .route(RequestPredicates.path("/aggregate/user/v3/api-docs"), http())
                .filter(setPath("/api-docs"))
                .build();
    }

}
