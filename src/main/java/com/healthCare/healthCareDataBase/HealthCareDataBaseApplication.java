package com.healthCare.healthCareDataBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages="com.healthCare.healthCareDataBase.Repository")
@SpringBootApplication
public class HealthCareDataBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareDataBaseApplication.class, args);
	}

}
