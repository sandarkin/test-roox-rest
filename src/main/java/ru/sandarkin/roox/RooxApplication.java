package ru.sandarkin.roox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class RooxApplication {

	public static void main(String[] args) {
		SpringApplication.run(RooxApplication.class, args);
	}
}
