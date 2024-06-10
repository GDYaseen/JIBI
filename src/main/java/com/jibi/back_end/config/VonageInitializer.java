package com.jibi.back_end.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.vonage.client.VonageClient;

@Configuration
public class VonageInitializer {

    private final VonageConfig vonageConfig;
    private final static Logger LOGGER = LoggerFactory.getLogger(VonageInitializer.class);
    public static VonageClient vonageClient; 
    @Autowired
    public VonageInitializer(VonageConfig vonageConfig) {
        this.vonageConfig = vonageConfig;
        vonageClient = VonageClient.builder()
                .apiKey(vonageConfig.getAccountSid())
                .apiSecret(vonageConfig.getAuthToken())
                .build();
        LOGGER.info("Twilio initialized ... [SID:" + vonageConfig.getAccountSid() + "]");
    }
}
