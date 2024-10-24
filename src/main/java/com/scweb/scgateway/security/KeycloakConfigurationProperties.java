package com.scweb.scgateway.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("keycloak")
public class KeycloakConfigurationProperties {

    private String realm;
    private String serverUrl;
    private String clientId;
    private String clientSecret;

}
