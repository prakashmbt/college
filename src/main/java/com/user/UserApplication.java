package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.user.config.ResponseConfig;

@SpringBootApplication
@PropertySource(value = {"classpath:response.properties"})
@EnableConfigurationProperties(value= {ResponseConfig.class})
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
