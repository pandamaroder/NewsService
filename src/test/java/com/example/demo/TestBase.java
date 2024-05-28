package com.example.demo;

import com.example.demo.model.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@ActiveProfiles("test")
@SpringBootTest()
@ContextConfiguration(initializers = PostgresInitializer.class)
public abstract class TestBase {

    protected <T> void assertThatNullableFieldsAreNotPrimitive(final Class<T> entityClass) {
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


}
