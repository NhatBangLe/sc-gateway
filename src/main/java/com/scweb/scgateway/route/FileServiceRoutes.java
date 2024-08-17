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
public class FileServiceRoutes {

    @Value("${FILE_SERVICE_BASE_URL}")
    private String fileServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> fileServiceRouter() {
        return GatewayRouterFunctions
                .route("file-service")
                .route(RequestPredicates.path("/api/file/**"), HandlerFunctions.http(fileServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fileServiceSwaggerRouter() {
        return GatewayRouterFunctions
                .route("file-service-swagger")
                .route(
                        RequestPredicates.path("/aggregate/file/v3/api-docs"),
                        HandlerFunctions.http(fileServiceUrl)
                )
                .filter(setPath("/api-docs"))
                .build();
    }

}
