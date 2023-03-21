package com.co.companion;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CompanionApplication {
	@Value("${TZ}")
	private String timeZone;
	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
	}
	public static void main(String[] args) {
		SpringApplication.run(CompanionApplication.class, args);
	}

}
