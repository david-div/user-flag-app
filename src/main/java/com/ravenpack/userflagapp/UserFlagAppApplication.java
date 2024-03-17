package com.ravenpack.userflagapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UserFlagAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserFlagAppApplication.class, args);
	}

}
