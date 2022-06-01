package com.elias.michalczuk.dynamodbspring;

import com.elias.michalczuk.dynamodbspring.redis.RedisProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RedisProperties.class)
public class DynamodbSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamodbSpringApplication.class, args);
	}

}
