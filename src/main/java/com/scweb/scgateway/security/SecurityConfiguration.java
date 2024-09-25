package com.scweb.scgateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final String[] freeResourceUrls = {"/swagger-ui/index.html", "/swagger-ui/**",
            "/aggregate/**", "/v3/api-docs/**", "/swagger-resources/**", "/api-docs/**"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorization ->
                authorization.requestMatchers(freeResourceUrls).permitAll()
                        .anyRequest().permitAll());
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
