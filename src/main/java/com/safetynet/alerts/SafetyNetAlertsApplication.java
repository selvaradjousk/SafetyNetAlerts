package com.safetynet.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

/**
 * SafetyNet Alerts Application.
 * @author Senthil
 *
 */
@Log4j2
@SpringBootApplication
public class SafetyNetAlertsApplication {


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		log.info("Application intizialised ..args.");
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
		log.info("App lauched and is running now ...");
	}
}
