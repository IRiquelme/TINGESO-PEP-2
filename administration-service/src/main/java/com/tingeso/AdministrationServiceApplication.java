package com.tingeso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AdministrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdministrationServiceApplication.class, args);
	}

}
