package com.capo.playwright_process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
public class PlaywrightProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaywrightProcessApplication.class, args);
	}

}
