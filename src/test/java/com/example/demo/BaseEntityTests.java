package com.example.demo;

import com.example.demo.helpers.DataHelper;
import com.example.demo.model.BaseEntity;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.*;


public class BaseEntityTests extends DemoApplicationBaseConfigTests{
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void assertThatAllEntitiesDoesNotHavePrimitiveNullableFields() {
        final Set<Class<?>> allEntities = new Reflections("com.example.demo").getTypesAnnotatedWith(Entity.class);
        assertThat(allEntities).hasSize(4);
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected <T extends BaseEntity> void assertThatEntityIsCorrect(@Nonnull final Set<T> entities,
                                                                    @Nonnull final JpaRepository<T, Long> repository) {
        assertThat(entities)
                .as("The size of the collection must be greater than or equal to two")
                .hasSizeGreaterThanOrEqualTo(2);

        entities.forEach(e -> assertThat(e.getId())
                .as("The ID" +
                        " must be filled in before saving")
                .isNotNull());
        assertThat(entities.stream().map(BaseEntity::getId).distinct().count())
                .as("All identifiers must be unique %s", entities)
                .isEqualTo(entities.size());

        final List<T> saved = repository.saveAll(entities);
        assertThat(saved)
                .hasSameSizeAs(entities);


        final List<T> result = repository.findAll();
        assertThat(result)
                .hasSameSizeAs(entities);
        result.forEach(c -> assertThatNoException()
                .as("Метод toString не должен генерировать ошибок")
                .isThrownBy(c::toString)); // toString
    }

    @Test
    void testEntityIsCorrect() {
        // Создаем набор сущностей для тестирования
        Set<Category> entities = new HashSet<>();
        Category entity1 = DataHelper.prepareCategory().build();
        Category entity2 = Category.builder().name("test2").build();
        entities.add(entity1);
        entities.add(entity2);

        // Вызываем метод assertThatEntityIsCorrect для проверки сущностей
        assertThatEntityIsCorrect(entities, categoryRepository);
    }
}


