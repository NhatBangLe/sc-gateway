package com.scweb.scgateway.security;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;

import java.util.Map;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final KeycloakConfigurationProperties keycloakProperties;
    private final String[] policyFreeResources = {
            "/docs", "/api-docs/*", "/swagger-ui/index.html",
            "/swagger-ui/*", "/aggregate/*",
            "/v3/api-docs/*", "/swagger-resources/*",
            "/api/v1/user/auth/*"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .addFilterAfter(policyEnforcerFilter(), BearerTokenAuthenticationFilter.class);
        http
                .exceptionHandling(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    private ServletPolicyEnforcerFilter policyEnforcerFilter() {
        PolicyEnforcerConfig config = new PolicyEnforcerConfig();
        config.setRealm(keycloakProperties.getRealm());
        config.setResource(keycloakProperties.getClientId());
        config.setAuthServerUrl(keycloakProperties.getServerUrl());
        config.setCredentials(Map.of("secret", keycloakProperties.getClientSecret()));

        var pathConfigs = Stream.of(policyFreeResources)
                .map(resource -> {
                            var pathConfig = new PolicyEnforcerConfig.PathConfig();
                            pathConfig.setName("free-resource");
                            pathConfig.setType("free");
                            pathConfig.setPath(resource);
                            pathConfig.setEnforcementMode(PolicyEnforcerConfig.EnforcementMode.DISABLED);
                            return pathConfig;
                        }
                ).toList();
        config.setPaths(pathConfigs);

        return new ServletPolicyEnforcerFilter(request -> config);
    }

}
