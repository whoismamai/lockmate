package com.telenet.lockmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class LockmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockmateApplication.class, args);
	}

}
