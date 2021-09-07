package com.elias.michalczuk.dynamodbspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class DynamodbSpringApplication {

	public static void main(String[] args) {

		ReactorDebugAgent.init();
		SpringApplication.run(DynamodbSpringApplication.class, args);
	}

}
