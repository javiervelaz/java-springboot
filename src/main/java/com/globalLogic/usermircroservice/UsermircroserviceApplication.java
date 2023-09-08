package com.globalLogic.usermircroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.globalLogic.usermircroservice.model")
public class UsermircroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermircroserviceApplication.class, args);
	}

}
