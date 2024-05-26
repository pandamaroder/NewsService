package com.example.demo.services;
import com.example.demo.dto.CategoryCreateRequest;
import com.example.demo.dto.CategoryCreateResponse;
import com.example.demo.dto.NewsCreateRequest;
import com.example.demo.dto.NewsDto;
import com.example.demo.model.Category;
import com.example.demo.model.News;
import com.example.demo.repositories.CategoryRepository;
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
   public NewsDto createNews(NewsCreateRequest newsCreateRequest) {
       Category categoryResponse = categoryService
               .createIfNeedCategory(newsCreateRequest.getCategoryName());

       News news = News.builder()
               .title(newsCreateRequest.getTitle())
               .content(newsCreateRequest.getContent())
               //пытаемся понять по id - возвращает прокси объект
               .user(userRepository
                       .getReferenceById(newsCreateRequest.getUserId()))
               .category(categoryResponse)
               .build();

       newsRepository.save(news);
       return null;

   }
}
