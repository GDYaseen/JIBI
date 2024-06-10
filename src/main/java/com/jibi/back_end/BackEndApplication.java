package com.jibi.back_end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.jibi.back_end.config.VonageConfig;

@SpringBootApplication
@EnableConfigurationProperties(VonageConfig.class)
public class BackEndApplication {

	public static void main(String[] args) {
		// SMSService.sendSMS("hello", "+212 6 38350158");
		SpringApplication.run(BackEndApplication.class, args);
	}

}
