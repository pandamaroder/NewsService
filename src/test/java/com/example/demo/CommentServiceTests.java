package com.example.demo;

import com.example.demo.dto.NewsDto;
import com.example.demo.helpers.DataHelper;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommentServiceTests extends UserAwareTestBase {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void createNews() {
        final int countCategoryBefore = getEntriesCount(TABLE_NAME_CATEGORIES);
        final int countNewsBefore = getEntriesCount(TABLE_NAME_NEWS);

        final String categoryName = DataHelper.getAlphabeticString(10);
        final NewsDto news = createNewsForTest(categoryName, "testTitle", "Belochkin");

        final int countCategoryAfter = getEntriesCount(TABLE_NAME_CATEGORIES);
        final int countNewsAfter = getEntriesCount(TABLE_NAME_NEWS);

        assertThat(countCategoryAfter - countCategoryBefore)
            .isOne();
        assertThat(countNewsAfter - countNewsBefore)
            .isOne();
        assertThat(categoryRepository.findByName(categoryName))
            .isPresent();

        assertThat(news)
            .isNotNull();
        assertThat(news.getId())
            .isPositive();
        assertThat(news.getCategoryName())
            .isEqualTo(categoryName);
        assertThat(news.getContent())
            .isEqualTo(categoryName);
    }

    @Test
    void retrieveSortedByTitlePaginatedNews() {
        //forced to change user name -> no db data isolation in test
        final NewsDto newsForTest = createNewsForTest("nature", "Nature", "Belochkin2");
        final NewsDto newsForTest1 = createNewsForTest("sport", "sport", "Orehov");
        final NewsDto newsForTest2 = createNewsForTest("a", "abc", "Lesnoy");
        final List<NewsDto> retrievedNews = newsService.retrieveNews(0, 3, true);
        assertThat(retrievedNews)
            .hasSize(3);
        assertThat(retrievedNews)
            .containsExactly(newsForTest2, newsForTest, newsForTest1);
    }
}
