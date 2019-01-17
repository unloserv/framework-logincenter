package com.canghuang.logincenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@MapperScan("com.canghuang.logincenter.mapper")
@EnableCaching
public class LogincenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogincenterApplication.class, args);
	}
}
