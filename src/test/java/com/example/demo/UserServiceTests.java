package com.example.demo;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceTests extends DemoApplicationBaseConfigTests {
    @Autowired
    CategoryService categoryService;


    @BeforeEach
    void setUp() {
        CategoryCreateRequest testCategory = new CategoryCreateRequest("defaultCategory");
        categoryService.createCategory(testCategory);
    }
    @Test
    void createNewCategory() {



    }
}
