package com.example.demo;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.CommentCreateDto;
import com.example.demo.dto.NewsCreateRequest;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.helpers.DataHelper;
import com.example.demo.services.CategoryService;
import com.example.demo.services.CommentService;
import com.example.demo.services.NewsService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

public abstract class UserAwareTestBase extends TestBase {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    protected void prepareNewsWithUsers(final String categoryName) {
        final UserCreateResponse petrPetrov = userService.createUser(new UserCreateRequest("Zaichikov"));
        final UserCreateResponse userOther = userService.createUser(new UserCreateRequest("Belkin"));
        newsService.createNews(NewsCreateRequest.builder().title("test1").content("test1")
            .userId(petrPetrov.userId()).categoryName(categoryName).build());
        newsService.createNews(NewsCreateRequest.builder().title("testOther1").content("testOther1")
            .userId(userOther.userId()).categoryName(categoryName).build());
    }

    protected void prepareNewsWithUsersAndComments(final String categoryName) {
        final UserCreateResponse petrPetrov = userService.createUser(new UserCreateRequest("Petrov"));
        final UserCreateResponse userOther = userService.createUser(new UserCreateRequest("Callinial"));
        final NewsDto news = newsService.createNews(NewsCreateRequest.builder().title("test").content("test")
            .userId(petrPetrov.userId()).categoryName(categoryName).build());
        final NewsDto news1 = newsService.createNews(NewsCreateRequest.builder().title("testOther").content("testOther")
            .userId(userOther.userId()).categoryName(categoryName).build());
        final CommentCreateDto commentDto = CommentCreateDto.builder().newsId(news.getId())
            .userId(petrPetrov.userId())
            .text("Comment#1").build();

        final CommentCreateDto commentDto2 = CommentCreateDto.builder().newsId(news.getId())
            .userId(petrPetrov.userId())
            .text("Comment#2").build();

        final CommentCreateDto commentDto3 = CommentCreateDto.builder().newsId(news1.getId())
            .userId(petrPetrov.userId())
            .text("Comment#3").build();

        final CommentCreateDto commentDto4 = CommentCreateDto.builder().newsId(news1.getId())
            .userId(userOther.userId())
            .text("Comment#4").build();
        commentService.createComment(commentDto);
        commentService.createComment(commentDto2);
        commentService.createComment(commentDto3);
        commentService.createComment(commentDto4);
    }

    protected NewsDto createNewsForTest(String category, String title, String userName) {

        final UserCreateResponse testUser = userService.createUser(new UserCreateRequest(userName));
        final NewsCreateRequest newsDto = NewsCreateRequest
            .builder()
            .categoryName(category)
            .content(category)
            .title(title)
            .userId(testUser.userId()).build();

        return newsService.createNews(newsDto);
    }
}
