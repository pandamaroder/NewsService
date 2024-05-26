package com.example.demo;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class BaseEntityTests extends DemoApplicationBaseConfigTests{

    @Test
    void assertThatAllEntitiesDoesNotHavePrimitiveNullableFields() {
        final Set<Class<?>> allEntities = new Reflections("com.example.demo").getTypesAnnotatedWith(Entity.class);
        assertThat(allEntities).hasSize(3);
        allEntities.forEach(this::assertThatNullableFieldsAreNotPrimitive);
    }

    private <T> void assertThatNullableFieldsAreNotPrimitive(final Class<T> entityClass) {
        Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class) &&
                        field.getAnnotation(Column.class).nullable()
                )
                .forEach(field -> assertThat(field.getType().isPrimitive())
                        .withFailMessage(String.format("In %s field %s is primitive", entityClass.getName(), field.getName()))
                        .isFalse()
                );
    }

}
