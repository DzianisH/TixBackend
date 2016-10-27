package org.tix;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TixApplication {
	public static void main(String[] args) {
		final Logger logger = Logger.getLogger(TixApplication.class);
		logger.info("Launching application");
		SpringApplication.run(TixApplication.class, args);
		logger.info("Application initialized successfully");
	}
}
