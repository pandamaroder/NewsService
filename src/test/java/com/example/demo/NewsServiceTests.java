package com.example.demo;

import com.example.demo.dto.*;
import com.example.demo.helpers.DataHelper;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.NewsRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.NewsService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.xml.crypto.Data;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class NewsServiceTests extends TestBase{

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void createNews() {
        UserCreateResponse testUser = userService.createUser(new UserCreateRequest("TestUser"));
        //TODO нужна ли проверка на созданного пользователя?
        //String countRowsSql = "SELECT id FROM demo.users where username = "TestUser";
        //Integer countCategoryBefore = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        //jdbcTemplate.execute();

        String countRowsSql = "SELECT COUNT(*) FROM demo.news";
        String countRowsSql1 = "SELECT COUNT(*) FROM demo.categories";


        Integer countCategoryBefore = jdbcTemplate.queryForObject(countRowsSql1, Integer.class);
        Integer countNewsBefore = jdbcTemplate.queryForObject(countRowsSql, Integer.class);

        String categoryName = DataHelper.getAlphabeticString(10);
        NewsDto newsDto = NewsDto.builder().categoryName(categoryName).content(categoryName)
                .title("testTitle")
                        .userId(testUser.userId()).build();

        NewsDto news = newsService.createNews(newsDto);

        Integer countCategoryAfter = jdbcTemplate.queryForObject(countRowsSql1, Integer.class);
        Integer countNewsAfter = jdbcTemplate.queryForObject(countRowsSql, Integer.class);


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
