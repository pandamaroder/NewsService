package com.example.demo;

import com.example.demo.helpers.DataHelper;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;


public class BaseEntityTests extends TestBase {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void assertThatAllEntitiesDoesNotHavePrimitiveNullableFields() {
        final Set<Class<?>> allEntities = new Reflections("com.example.demo").getTypesAnnotatedWith(Entity.class);
        assertThat(allEntities).hasSize(4);
        allEntities.forEach(this::assertThatNullableFieldsAreNotPrimitive);
    }


    @Test
    void testEntityIsCorrect() {

        Set<Category> entities = new HashSet<>();
        Category entity1 = DataHelper.prepareCategory().build();
        Category entity2 = Category.builder().name("test2").build();
        entities.add(entity1);
        entities.add(entity2);
        assertThatEntityIsCorrect(entities, categoryRepository);
    }

}


