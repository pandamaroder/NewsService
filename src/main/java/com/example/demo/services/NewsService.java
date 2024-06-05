package com.example.demo.services;

import com.example.demo.dto.NewsDto;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.repositories.NewsRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    @Transactional
    public NewsDto createNews(NewsDto newsDto) {
        Category categoryResponse = categoryService
                .createIfNeedCategory(newsDto.getCategoryName());

        News news = News.builder()
                .title(newsDto.getTitle())
                .content(newsDto.getContent())
                //пытаемся понять по id - возвращает прокси объект
                .user(userRepository
                        .getReferenceById(newsDto.getUserId()))
                .category(categoryResponse)
                .build();

        News savedNews = newsRepository.save(news);
        return NewsDto.builder().id(savedNews.getId()).categoryName(newsDto.getCategoryName())
                .content(newsDto.getContent())
                .title(newsDto.getTitle())
                .userId(newsDto.getUserId()).build();

    }

    @Transactional
    public NewsDto deleteNews(long newsId) {
        //TODO при удалении новости -> каскадное удаление всех связанных комментариев
        return null;
    }

}
