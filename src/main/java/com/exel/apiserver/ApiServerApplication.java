package com.exel.apiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//TODO replace @Param("userId") with @RequestAttribute("userId) after integrating Auth0
@SpringBootApplication
public class ApiServerApplication {

	@PostConstruct
	protected void init () {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main (String[] args) {
		SpringApplication.run(ApiServerApplication.class, args);
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor () {
		return new MethodValidationPostProcessor();
	}
}
