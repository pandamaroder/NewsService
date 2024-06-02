package com.example.demo;

import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.CategoryDto;
import com.example.demo.helpers.DataHelper;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import jakarta.validation.constraints.Negative;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CategoryServiceTests extends TestBase{
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;


   /* @Container
    static PostgreSQLContainer<?> pgDBContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.1"));


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", pgDBContainer::getJdbcUrl);
        registry.add("spring.datasource.username", pgDBContainer::getUsername);
        registry.add("spring.datasource.password", pgDBContainer::getPassword);
    }*/

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


   @Test
    void updateCategory() {
        String countRowsSql = "SELECT COUNT(*) FROM demo.categories";

        String categoryName = DataHelper.getAlphabeticString(10);
        CategoryCreateResponse testCategory = categoryService
                .createCategory(new CategoryCreateRequest(categoryName));
        Integer countCategoryBefore = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        String updatedCategoryName = DataHelper.getAlphabeticString(5);
        CategoryDto updatedCategory = new CategoryDto(testCategory.id(), updatedCategoryName);
       //TODO update не возможен тк categoryName никальнок
        CategoryDto categoryDto = categoryService.updateCategory(updatedCategory);

        Integer countCategoryAfter = jdbcTemplate.queryForObject(countRowsSql, Integer.class);
        assertThat(countCategoryAfter-countCategoryBefore)
                .isEqualTo(0);

        assertThat(testCategory)
                .isNotNull();
        assertThat(categoryDto.id())
                .isPositive();
        assertThat(categoryDto.name())
                .isEqualTo(categoryName);

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testCreateIfNeedCategoryInTransaction() {

        String categoryName = "testCategory";
        assertThat(categoryRepository.findByName(categoryName))
                .isNotPresent();


        Category category = categoryService.createIfNeedCategory(categoryName);
        assertThat(categoryRepository.findByName(categoryName))
                .isNotNull();
        assertThat(category)
                .isNotNull();
        assertThat(category.getId())
                .isPositive();
        assertThat(category.getName())
                .isEqualTo(categoryName);

    }

    @Test
    public void testCreateIfNeedCategoryWithoutTransaction() {
        // Попробуем вызвать метод вне существующей транзакции и ожидаем исключение
        String categoryName = "testCategoryNoTransaction";

        IllegalTransactionStateException exception = assertThrows(
                IllegalTransactionStateException.class,
                () -> categoryService.createIfNeedCategory(categoryName)
        );
        assertThat(exception.getMessage())
                .isNotNull()
                .isEqualTo("No existing transaction found for transaction marked with propagation 'mandatory'");
         }

}
