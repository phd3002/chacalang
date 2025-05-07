package com.thungcam.chacalang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ChacalangApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChacalangApplication.class, args);
	}

}
