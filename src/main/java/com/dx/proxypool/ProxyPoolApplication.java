package com.dx.proxypool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@SpringBootApplication
@EnableScheduling
public class ProxyPoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyPoolApplication.class, args);
	}
}
