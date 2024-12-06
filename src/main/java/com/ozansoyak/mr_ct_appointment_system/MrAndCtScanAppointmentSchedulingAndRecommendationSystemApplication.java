package com.ozansoyak.mr_ct_appointment_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MrAndCtScanAppointmentSchedulingAndRecommendationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MrAndCtScanAppointmentSchedulingAndRecommendationSystemApplication.class, args);
	}

}
