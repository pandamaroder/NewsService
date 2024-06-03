package com.example.demo;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.helpers.DataHelper;
import com.example.demo.services.CategoryService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;


public class UserServiceTests extends TestBase {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createUser() {

        int countCategoryBefore = getEntriesCount("demo.users");
        String userName = DataHelper.getAlphabeticString(10);
        UserCreateResponse testUser = userService
                .createUser(new UserCreateRequest(userName));
        int countCategoryAfter = getEntriesCount("demo.users");
        assertThat(countCategoryAfter-countCategoryBefore)
                .isOne();

        assertThat(testUser)
                .isNotNull();
        assertThat(testUser.userId())
                .isPositive();
        assertThat(testUser.username())
                .isEqualTo(userName);

    }
}
