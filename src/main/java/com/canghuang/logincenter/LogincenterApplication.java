package com.canghuang.logincenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableCaching
public class LogincenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogincenterApplication.class, args);
	}
}
