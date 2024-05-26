package com.example.demo;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.CategoryDto;
import com.example.demo.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class CategoryServiceTests extends DemoApplicationBaseConfigTests {
    @Autowired
    CategoryService categoryService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createNewCategory() {
        String countRowsSql = "SELECT COUNT(*) FROM demo.categories";
        Integer countCategoryBefore = jdbcTemplate.queryForObject(countRowsSql, Integer.class);

        CategoryCreateResponse testCategory = categoryService
                .createCategory(new CategoryCreateRequest("New Category"));
        Integer countCategoryAfter = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        assertThat(countCategoryAfter-countCategoryBefore)
                .isOne();

        assertThat(testCategory)
                .isNotNull();
        assertThat(testCategory.id())
                .isPositive();
        assertThat(testCategory.name())
                .isEqualTo("New Category");


    }


    @Test
    void deleteCategory() {
        String countRowsSql = "SELECT COUNT(*) FROM demo.categories";


        String name = UUID.randomUUID().toString();

        CategoryCreateResponse testCategory = categoryService
                .createCategory(new CategoryCreateRequest(name));
        Integer countCategoryBefore = jdbcTemplate.queryForObject(countRowsSql, Integer.class);

        CategoryDto categoryDto = categoryService.deleteCategory(testCategory.id());
        Integer countCategoryAfter = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        assertThat(countCategoryAfter)
                .isEqualTo(0);
        assertThat(categoryDto)
                .isNotNull();
        assertThat(categoryDto.id())
                .isPositive();
        assertThat(categoryDto.name())
                .isEqualTo(name);



    }


    @Test
    void updateCategory() {
        /*CategoryDto testCategoryDto = new CategoryDto();
        CategoryDto categoryDto = categoryService.updateCategory(testCategoryDto);

        assertThat(categoryDto)
                .isNotNull();
        assertThat(categoryDto.id())
                .isPositive();
        assertThat(categoryDto.name())
                .isEqualTo(name);
                .isEqualTo("Changed Category");
    }*/
    }
}
