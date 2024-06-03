package com.example.demo;

import com.example.demo.dto.NewsDto;
import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.helpers.DataHelper;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.NewsService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class NewsServiceTests extends TestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    void createNews() {
        UserCreateResponse testUser = userService.createUser(new UserCreateRequest("TestUser"));

        int countCategoryBefore = getEntriesCount("demo.categories");
        Integer countNewsBefore = getEntriesCount("demo.news");

        String categoryName = DataHelper.getAlphabeticString(10);
        NewsDto newsDto = NewsDto.builder().categoryName(categoryName).content(categoryName)
                .title("testTitle")
                .userId(testUser.userId()).build();

        NewsDto news = newsService.createNews(newsDto);

        Integer countCategoryAfter = getEntriesCount("demo.categories");
        Integer countNewsAfter = getEntriesCount("demo.news");


        assertThat(countCategoryAfter - countCategoryBefore)
                .isOne();
        assertThat(countNewsAfter - countNewsBefore)
                .isOne();
        assertThat(categoryRepository.findByName(categoryName))
                .isPresent();

        assertThat(news)
                .isNotNull();
        assertThat(news.getId())
                .isPositive();
        assertThat(news.getCategoryName())
                .isEqualTo(categoryName);
        assertThat(news.getContent())
                .isEqualTo(categoryName);
    }

}
