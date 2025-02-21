package com.projectx.foundit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FounditApplication {

	public static void main(String[] args) {
		SpringApplication.run(FounditApplication.class, args);
	}

}
