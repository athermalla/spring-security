package com.fresco.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);


//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		String rawPassword = "pass_word";
//		String encodedPassword = passwordEncoder.encode(rawPassword);
//
//		System.out.println("Encoded Password: " + encodedPassword);
	}
}
