package com.example.demo.persistance;

import com.example.demo.helpers.DataHelper;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    TestEntityManager entityManager;

       /* @BeforeEach
    public void setUp() {
        // Подготовка данных без использования методов репозитория
        Category testCategory = DataHelper.prepareCategory().build();
        entityManager.persist(testCategory);
        entityManager.flush();
    }*/

    @Test
    void givenNewCategory_whenSaved_thenSuccess() {
        Category testCategory = DataHelper.prepareCategory().build();
        categoryRepository.save(testCategory);
        assertThat(entityManager.find(Category.class, testCategory.getId()))
                .isEqualTo(testCategory);
    }

    @Test
    void givenCategoryCreated_whenFindById_thenSuccess() {
        Category testCategory = DataHelper.prepareCategory().build();

        entityManager.persist(testCategory);

        Optional<Category> retrievedCategory = categoryRepository.findById(testCategory.getId());
        assertThat(retrievedCategory)
                .contains(testCategory);

    }

    @Test
    void givenCategoryCreated_whenFindByNameContaining_thenSuccess() {
        Category testCategory1 = DataHelper.preparePlainCategory().name("test1").build();
        Category testCategory2 = DataHelper.preparePlainCategory().name("test2").build();
         entityManager.persist(testCategory1);
        entityManager.persist(testCategory2);
        Iterable<Category> categories = categoryRepository.findByNameContaining("test");
        assertThat(categories)
                .contains(testCategory1, testCategory2);
    }

    @Test
    void givenCategoryCreated_whenUpdate_thenSuccess() {
        Category newCategory = DataHelper.preparePlainCategory().name("000001").build();
        entityManager.persist(newCategory);
        String newName = "New Category 001";
        newCategory.setName(newName);
        categoryRepository.save(newCategory);
        assertThat(entityManager.find(Category.class, newCategory.getId()).getName())
                .isEqualTo(newName);
    }

    @Test
    void givenCategoryCreated_whenDelete_thenSuccess() {
        Category newCategory = DataHelper.preparePlainCategory().name("000001").build();
        entityManager.persist(newCategory);
        categoryRepository.delete(newCategory);
        assertThat(entityManager.find(Category.class, newCategory.getId()))
                .isNull();
    }
}
