package com.epam.bookstoreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BookstoreserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreserviceApplication.class, args);
	}

}
