package com.example.demo;

import com.example.demo.dto.*;
import com.example.demo.helpers.DataHelper;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import com.example.demo.services.NewsService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CategoryServiceTests extends TestBase{

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NewsService newsService;





    private CategoryCreateResponse createCategory(String categoryName) {
        CategoryCreateResponse testCategory = categoryService
                .createCategory(new CategoryCreateRequest(categoryName));
        return testCategory;
    }
    @Test
    void createNewCategory() {
        String categoryName = DataHelper.getAlphabeticString(10);

        int countCategoryBefore = getEntriesCount("demo.categories");
        CategoryCreateResponse testCategory = createCategory(categoryName);
        int countCategoryAfter = getEntriesCount("demo.categories");

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
        String name = UUID.randomUUID().toString();
        createCategory(DataHelper.getAlphabeticString(10));
        CategoryCreateResponse testCategory = createCategory(name);

        int countCategoryBefore = getEntriesCount("demo.categories");
        CategoryDto categoryDto = categoryService.deleteCategory(testCategory.id());
        int countCategoryAfter = getEntriesCount("demo.categories");

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
        CategoryCreateResponse testCategory = createCategory(DataHelper.getAlphabeticString(10));

        int countCategoryBefore = getEntriesCount("demo.categories");
        String updatedCategoryName = DataHelper.getAlphabeticString(5);
        CategoryDto updatedCategory = new CategoryDto(testCategory.id(), updatedCategoryName);
        CategoryDto categoryDtoAfterUpdate = categoryService.updateCategory(updatedCategory);
        int countCategoryAfter = getEntriesCount("demo.categories");

        assertThat(countCategoryAfter-countCategoryBefore)
                .isZero();
        assertThat(testCategory)
                .isNotNull();
        assertThat(categoryDtoAfterUpdate.id())
                .isPositive();
        assertThat(categoryDtoAfterUpdate.name())
                .isEqualTo(updatedCategoryName);

    }

    @Test
    void testCreateIfNeedCategoryInTransaction() {
        String categoryName = "testCategory";
        assertThat(categoryRepository.findByName(categoryName))
                .isNotPresent();

        Category category = transactionTemplate.execute(status -> {
            try {
                return categoryService.createIfNeedCategory(categoryName);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
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
    void testCreateIfNeedCategoryWithoutTransaction() {
        String categoryName = "testCategoryNoTransaction";

        assertThatThrownBy(() -> categoryService
                .createIfNeedCategory(categoryName))
                .isInstanceOf(IllegalTransactionStateException.class)
                .hasMessage("No existing transaction found for transaction marked with propagation 'mandatory'");
         }


    @Test
    void deleteCategoryWithNews() {

        String categoryName = DataHelper.getAlphabeticString(10);
        String categoryNameOther = DataHelper.getAlphabeticString(10);
        CategoryCreateResponse testCategory = createCategory(categoryName);
        prepareNewsWithUsers(categoryName);
        CategoryCreateResponse testCategoryOther = createCategory(categoryNameOther);

        int countCategoryBefore = getEntriesCount("demo.categories");


        CategoryDto categoryDto = categoryService.deleteCategory(testCategory.id());
        int countCategoryAfter = getEntriesCount("demo.categories");;
        assertThat(countCategoryBefore - countCategoryAfter)
                .isOne();
        assertThat(categoryDto)
                .isNotNull();
        assertThat(categoryDto.id())
                .isPositive();
        assertThat(categoryDto.name())
                .isEqualTo(categoryName);
    }



}
