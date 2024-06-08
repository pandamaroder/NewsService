package com.example.demo.repositories;

import com.example.demo.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, PagingAndSortingRepository<News, Long> {

    // List<News> findByDateCreatedBetween(LocalDate start, LocalDate end);

    Iterable<News> findByTitleContaining(String title);

    @Override
    @EntityGraph(value = "retrieve-news-entity-with-attr", type = EntityGraph.EntityGraphType.LOAD)
    Page<News> findAll(Pageable pageable);
}
