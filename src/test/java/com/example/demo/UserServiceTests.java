package com.example.demo;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.helpers.DataHelper;
import com.example.demo.services.CategoryService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;


public class UserServiceTests extends TestBase {
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {

    }
    @Test
    void createUser() {
        String countRowsSql = "SELECT COUNT(*) FROM demo.users";
        Integer countCategoryBefore = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        String categoryName = DataHelper.getAlphabeticString(10);
        CategoryCreateResponse testCategory = categoryService
                .createCategory(new CategoryCreateRequest(categoryName));
        Integer countCategoryAfter = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        assertThat(countCategoryAfter-countCategoryBefore)
                .isOne();

        assertThat(testCategory)
                .isNotNull();
        assertThat(testCategory.id())
                .isPositive();
        assertThat(testCategory.name())
                .isEqualTo(categoryName);



    }
}
