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

class UserServiceTests extends TestBase {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createUser() {
        final int countCategoryBefore = getEntriesCount(TABLE_NAME_USERS);
        final String userName = DataHelper.getAlphabeticString(10);
        final UserCreateResponse testUser = userService
                .createUser(new UserCreateRequest(userName));
        final int countCategoryAfter = getEntriesCount(TABLE_NAME_USERS);
        assertThat(countCategoryAfter - countCategoryBefore)
                .isOne();

        assertThat(testUser)
                .isNotNull();
        assertThat(testUser.userId())
                .isPositive();
        assertThat(testUser.username())
                .isEqualTo(userName);
    }
}
