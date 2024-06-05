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
class NewsServiceTests extends TestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryRepository categoryRepository;



    @Test
    void createNews() {
        final UserCreateResponse testUser = userService.createUser(new UserCreateRequest("TestUser"));

        final int countCategoryBefore = getEntriesCount(TABLE_NAME_CATEGORIES);
        final  int countNewsBefore = getEntriesCount(TABLE_NAME_NEWS);

        final  String categoryName = DataHelper.getAlphabeticString(10);
        final NewsDto newsDto = NewsDto.builder().categoryName(categoryName).content(categoryName)
                .title("testTitle")
                .userId(testUser.userId()).build();

        final NewsDto news = newsService.createNews(newsDto);

        final  int  countCategoryAfter = getEntriesCount(TABLE_NAME_CATEGORIES);
        final int  countNewsAfter = getEntriesCount(TABLE_NAME_NEWS);


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
