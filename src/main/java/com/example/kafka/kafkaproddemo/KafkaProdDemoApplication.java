package com.example.kafka.kafkaproddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.kafka")
@SpringBootApplication
@EntityScan("com.example.kafka")
@ComponentScan("com.example.kafka")
public class KafkaProdDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProdDemoApplication.class, args);
	}

}

