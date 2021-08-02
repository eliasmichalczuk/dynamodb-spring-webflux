package com.elias.michalczuk.dynamodbspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
public class DynamodbSpringApplication {

	public static void main(String[] args) {
		System.setProperty("aws.accessKeyId", "super-access-key");
		System.setProperty("aws.secretKey", "super-secret-key");
		System.setProperty("aws.region", "us-east-1");
		SpringApplication.run(DynamodbSpringApplication.class, args);
	}

}
