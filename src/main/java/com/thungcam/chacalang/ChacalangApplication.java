package com.thungcam.chacalang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class ChacalangApplication {

	public static void main(String[] args) {
//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
//		System.out.println("Current JVM timezone: " + ZoneId.systemDefault());
		SpringApplication.run(ChacalangApplication.class, args);
	}

}
