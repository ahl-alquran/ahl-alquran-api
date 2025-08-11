package com.ahl.alquran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AhlAlQuranApplication {

	public static void main(String[] args) {
		SpringApplication.run(AhlAlQuranApplication.class, args);
	}

}
