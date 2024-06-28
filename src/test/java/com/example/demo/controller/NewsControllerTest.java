package com.example.demo.controller;

import com.example.demo.UserAwareTestBase;
import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.helpers.DataHelper;
import com.example.demo.services.CategoryService;
import com.example.demo.services.NewsService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

class NewsControllerTest extends UserAwareTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private NewsService newsService;

    @Test
    void updateNewsNegative() {
        final UserCreateResponse petrZaichikov = userService.createUser(new UserCreateRequest("Zaichikov"));

        final String categoryName = DataHelper.getAlphabeticString(10);

        final CategoryCreateResponse testCategory = categoryService.createCategory(new CategoryCreateRequest("test"));
        final NewsDto news = createNewsForTest(categoryName, "testTitle", "Belochkin");

        final var newsDto = NewsDto.builder()
            .id(news.getId())
            .userId(news.getUserId())
            .categoryName(testCategory.name())
            .content("content")
            .title("title")
            .build();

        final var result = webTestClient.put()
            .uri("/news")
            .header("userId", "1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newsDto)
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    void updateNewsPositive() {
        final UserCreateResponse petrZaichikov = userService.createUser(new UserCreateRequest("Zaichikov"));

        final String categoryName = DataHelper.getAlphabeticString(10);

        final CategoryCreateResponse testCategory = categoryService.createCategory(new CategoryCreateRequest("test"));
        final NewsDto news = createNewsForTest(categoryName, "testTitle", "Belochkin");

        final var newsDto = NewsDto.builder()
            .id(news.getId())
            .userId(news.getUserId())
            .categoryName(testCategory.name())
            .content("content")
            .title("title")
            .build();

        final var result = webTestClient.put()
            .uri("/news")
            .header("userId", "2")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newsDto)
            .exchange()
            .expectStatus().isOk()
            .expectBody(NewsDto.class)
            .returnResult();
        assertThat(result)
            .isNotNull();
    }
}
