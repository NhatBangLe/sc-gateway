package com.scweb.scgateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("app")
public class AppConfigProps {

    private String userServiceUrl;
    private String projectServiceUrl;
    private String chatServiceUrl;
    private String fileServiceUrl;
    private String postServiceUrl;

}
