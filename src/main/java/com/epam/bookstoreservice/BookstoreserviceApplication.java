package com.epam.bookstoreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BookstoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreServiceApplication.class, args);
	}

}
