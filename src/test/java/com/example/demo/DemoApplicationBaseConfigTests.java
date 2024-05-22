package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
abstract class DemoApplicationBaseConfigTests {


	@Container
	static PostgreSQLContainer<?> pgDBContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.1"))
			.waitingFor(Wait.forListeningPort());
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", pgDBContainer::getJdbcUrl);
		registry.add("spring.datasource.username", pgDBContainer::getUsername);
		registry.add("spring.datasource.password", pgDBContainer::getPassword);
	}


}
