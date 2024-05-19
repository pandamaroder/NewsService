package com.example.demo.persistance;

import com.example.demo.helpers.DataHelper;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.NewsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class NewsRepositoryTest {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    TestEntityManager entityManager;

       /* @BeforeEach
    public void setUp() {
        // Подготовка данных без использования методов репозитория
        Category testCategory = DataHelper.prepareCategory().build();
        entityManager.persist(testCategory);
        entityManager.flush();
    }*/

}
