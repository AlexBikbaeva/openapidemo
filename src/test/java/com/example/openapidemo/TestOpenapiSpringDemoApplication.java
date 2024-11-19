package com.example.openapidemo;

import org.springframework.boot.SpringApplication;

public class TestOpenapiSpringDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(OpenapiSpringDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
