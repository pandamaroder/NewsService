package com.example.demo;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.CategoryDto;
import com.example.demo.helpers.DataHelper;
import com.example.demo.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class CategoryServiceTests extends TestBase {
    @Autowired
    CategoryService categoryService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createNewCategory() {
        String countRowsSql = "SELECT COUNT(*) FROM demo.categories";
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


    @Test
    void deleteCategory() {
        String countRowsSql = "SELECT COUNT(*) FROM demo.categories";


        String name = UUID.randomUUID().toString();

        CategoryCreateResponse testCategory = categoryService
                .createCategory(new CategoryCreateRequest(name));
        Integer countCategoryBefore = jdbcTemplate.queryForObject(countRowsSql, Integer.class);

        CategoryDto categoryDto = categoryService.deleteCategory(testCategory.id());
        Integer countCategoryAfter = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        assertThat(countCategoryBefore - countCategoryAfter)
                .isOne();
        assertThat(categoryDto)
                .isNotNull();
        assertThat(categoryDto.id())
                .isPositive();
        assertThat(categoryDto.name())
                .isEqualTo(name);



    }


   /* @Test
    void updateCategory() {
        String countRowsSql = "SELECT COUNT(*) FROM demo.categories";

        String categoryName = DataHelper.getAlphabeticString(10);
        CategoryCreateResponse testCategory = categoryService
                .createCategory(new CategoryCreateRequest(categoryName));
        Integer countCategoryBefore = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        String updatedCategoryName = DataHelper.getAlphabeticString(5);
        CategoryDto updatedCategory = new CategoryDto(testCategory.id(), updatedCategoryName);
        CategoryDto categoryDto = categoryService.updateCategory(updatedCategory);

        Integer countCategoryAfter = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        assertThat(countCategoryAfter-countCategoryBefore)
                .isEqualTo(0);

        assertThat(testCategory)
                .isNotNull();
        assertThat(categoryDto.id())
                .isPositive();
        assertThat(categoryDto.name())
                .isEqualTo(updatedCategoryName);

    }*/
}
