package com.example.demo;

import com.example.demo.dto.CommentDto;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.services.CommentService;
import com.example.demo.services.NewsService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = PostgresInitializer.class)
public abstract class UserAwareTestBase extends TestBase {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentservice;

    protected void prepareNewsWithUsers(final String categoryName) {
        final UserCreateResponse petrPetrov = userService.createUser(new UserCreateRequest("Petrov"));
        final UserCreateResponse userOther = userService.createUser(new UserCreateRequest("Callinial"));
        newsService.createNews(NewsDto.builder().title("test").content("test")
            .userId(petrPetrov.userId()).categoryName(categoryName).build());
        newsService.createNews(NewsDto.builder().title("testOther").content("testOther")
            .userId(userOther.userId()).categoryName(categoryName).build());
    }

    protected void prepareNewsWithUsersAndComments(final String categoryName) {
        final UserCreateResponse petrPetrov = userService.createUser(new UserCreateRequest("Petrov"));
        final UserCreateResponse userOther = userService.createUser(new UserCreateRequest("Callinial"));
        NewsDto news = newsService.createNews(NewsDto.builder().title("test").content("test")
                .userId(petrPetrov.userId()).categoryName(categoryName).build());
        NewsDto news1 = newsService.createNews(NewsDto.builder().title("testOther").content("testOther")
                .userId(userOther.userId()).categoryName(categoryName).build());
        CommentDto commentDto = CommentDto.builder().newsId(news.getId())
                .userId(petrPetrov.userId())
                .text("Comment#1").build();

        CommentDto commentDto2 = CommentDto.builder().newsId(news.getId())
                .userId(petrPetrov.userId())
                .text("Comment#2").build();

        CommentDto commentDto3 = CommentDto.builder().newsId(news1.getId())
                .userId(petrPetrov.userId())
                .text("Comment#3").build();

        CommentDto commentDto4 = CommentDto.builder().newsId(news1.getId())
                .userId(userOther.userId())
                .text("Comment#4").build();
        commentservice.createComment(commentDto);
        commentservice.createComment(commentDto2);
        commentservice.createComment(commentDto3);
        commentservice.createComment(commentDto4);
    }
}
