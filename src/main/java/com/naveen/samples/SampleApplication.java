package com.naveen.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class}) //If we want to disable whitelabel error page to let servlet container handle the error,
public class SampleApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}
}
