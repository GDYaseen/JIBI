package com.jibi.back_end.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "twilio")
@Data
public class VonageConfig {
    private String accountSid;
    private String authToken;
    private String number;
}
