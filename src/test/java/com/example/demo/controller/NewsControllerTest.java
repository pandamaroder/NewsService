package com.example.demo.controller;

import com.example.demo.TestBase;
import com.example.demo.dto.NewsCreateRequest;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.services.UserService;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NewsControllerTest extends TestBase {



    @Test
    void createNews() {

        final var newsCreateRequest = NewsCreateRequest.builder()
            .userId(1)
            .categoryName("test")
            .content("content")
            .title("title")
            .build();

        final var result = webTestClient.post()
                .uri("/news")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newsCreateRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(NewsDto.class)
                .returnResult();
        assertThat(result)
            .isNotNull();
     /*   assertThat(result)
                .isNotNull()
        assertThat(result.getResponseHeaders())
                .hasSizeGreaterThanOrEqualTo(2)
                .containsKeys(HttpHeaders.CONTENT_TYPE, HttpHeaders.LOCATION);

        final List<String> location = result.getResponseHeaders().get(HttpHeaders.LOCATION);
        assertThat(location)
                .isNotNull()
                .hasSize(1)
                .first(InstanceOfAssertFactories.STRING)
                .startsWith("http://localhost:%d/api/employee/".formatted(port));

        final var createdEmployee = result.getResponseBody();
        assertThat(createdEmployee)
                .isNotNull()
                .satisfies(e -> {
                    assertThat(e.getId())
                            .isNotNull();
                    assertThat(e.getFirstName())
                            .isEqualTo("John");
                    assertThat(e.getLastName())
                            .isEqualTo("Doe");
                    assertThat(e.getStandardHoursPerDay())
                            .isEqualTo(8);
                    assertThat(e.getSalaryPerHour())
                            .isEqualByComparingTo("400.00");
                });

        final var foundEmployee = webTestClient.get()
                .uri(location.get(0))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(foundEmployee)
                .isNotNull()
                .satisfies(e -> assertThat(e.getId())
                        .isEqualTo(createdEmployee.getId()));

        final var allEmployees = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/employee/all")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto[].class)
                .returnResult()
                .getResponseBody();
        assertThat(allEmployees)
                .hasSize(1)
                .containsExactly(createdEmployee);*/
    }
}
