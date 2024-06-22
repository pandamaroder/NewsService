package com.example.demo.services;

import com.example.demo.annotations.RequireNewsAuthor;
import com.example.demo.dto.CommentDto;
import com.example.demo.dto.NewsCreateRequest;
import com.example.demo.dto.NewsDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.repositories.NewsRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService extends BaseService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    @Transactional
    @RequireNewsAuthor
    public NewsDto createNews(NewsCreateRequest newsDto) {
        final Category categoryResponse = categoryService
            .createIfNeedCategory(newsDto.getCategoryName());

        final News news = News.builder()
            .title(cleanData(newsDto.getTitle()))
            .content(cleanData(newsDto.getContent()))
            //пытаемся понять по id - возвращает прокси объект
            .user(userRepository
            .getReferenceById(newsDto.getUserId()))
            .category(categoryResponse)
            .build();

        final News savedNews = newsRepository.save(news);
        return NewsDto.builder().id(savedNews.getId()).categoryName(newsDto.getCategoryName())
            .content(newsDto.getContent())
            .title(newsDto.getTitle())
            .userId(newsDto.getUserId()).build();

    }

    @Transactional
    public List<NewsDto> retrieveNews(int pages, int elements, boolean showConfirmed) {
        final List<NewsDto> newsDtos = new ArrayList<>();
        List<News> newsList;
        if (showConfirmed) {
            final PageRequest of = PageRequest.of(pages, elements, Sort.by(Sort.Order.asc("title")));
            newsList = newsRepository.findAll(of).getContent();
        } else {
            newsList = newsRepository.findAll(PageRequest.of(pages, elements)).getContent();
        }

        for (final News news : newsList) {
            newsDtos.add(NewsDto.builder()
                .title(news.getTitle())
                .categoryName(news.getCategory().getName())
                .content(news.getContent())
                .userId(news.getUser().getId())
                .id(news.getId())
                .build());
        }
        return newsDtos;
    }

    @Transactional
    public NewsDto deleteNews(long newsId) {

        final News news = newsRepository
            .findById(newsId)
            .orElseThrow(() -> new NotFoundException("Новости с таким ID не существует"));
        newsRepository.delete(news);
        return NewsDto.builder()
            .categoryName(news.getCategory().getName())
            .content(news.getContent())
            .title(news.getTitle())
            .userId(news.getUser().getId())
            .id(news.getId())
            .build();
    }

    @Transactional
    public Collection<NewsDto> findByTitle(String title) {
        final Iterable<News> allNews = newsRepository.findByTitleContaining(title);
        final Set<NewsDto> newsDtos = new HashSet<>();

        for (final News news : allNews) {
            newsDtos.add(NewsDto.builder()
                .title(news.getTitle())
                .categoryName(news.getCategory().getName())
                .content(news.getContent())
                .userId(news.getUser().getId())
                .id(news.getId())
                .build());
        }
        return newsDtos;

    }

    @Transactional
    public CommentDto updateNews(NewsDto newsDto) {
        //TODO комментарии редактировать по счетчику
        return null;
    }
}
