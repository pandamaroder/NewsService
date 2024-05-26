package com.example.demo;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.CategoryDto;
import com.example.demo.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


public class CategoryServiceTests extends DemoApplicationBaseConfigTests {
    @Autowired
    CategoryService categoryService;


    @BeforeEach
    void setUp() {
        CategoryCreateRequest testCategory = new CategoryCreateRequest("defaultCategory");
        categoryService.createCategory(testCategory);
    }


    @Test
    void createNewCategory() {
        CategoryCreateResponse testCategory = categoryService
                .createCategory(new CategoryCreateRequest("New Category"));
        assertThat(testCategory)
                .isNotNull();
        assertThat(testCategory.id())
                .isNotNull();
        assertThat(testCategory.name())
                .isEqualTo("New Category");
    }


    @Test
    void deleteCategory() {
        CategoryDto categoryDto = categoryService.deleteCategory(1);

        assertThat(categoryDto)
                .isNotNull();
        assertThat(categoryDto.getId())
                .isNotNull();
        assertThat(categoryDto.getName())
                .isEqualTo("defaultCategory");
    }


    @Test
    void updateCategory() {
        CategoryDto testCategoryDto = CategoryDto.builder().name("Changed Category").build();
        CategoryDto categoryDto = categoryService.updateCategory(testCategoryDto);

        assertThat(categoryDto)
                .isNotNull();
        assertThat(categoryDto.getId())
                .isNotNull();
        assertThat(categoryDto.getName())
                .isEqualTo("Changed Category");
    }
}
