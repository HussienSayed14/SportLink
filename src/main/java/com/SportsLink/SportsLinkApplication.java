package com.SportsLink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SportsLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsLinkApplication.class, args);
	}

}
