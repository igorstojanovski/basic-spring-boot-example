package org.igorski.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * jdbc:h2:mem:testdb
 */
@SpringBootApplication
public class SimpleExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleExampleApplication.class, args);
	}
}
