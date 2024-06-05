package com.example.demo.services;

import com.example.demo.dto.CommentDto;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.NewsRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final NewsRepository newsRepository;

    @Transactional
    public CommentDto createComment(CommentDto commentDto) {
        User userToAdjust = userRepository.getReferenceById(commentDto.getUserId());
        News newsToAdjust = newsRepository.getReferenceById(commentDto.getNewsId());
        Comment comment = com.example.demo.model.Comment.builder()
                .news(newsToAdjust)
                .user(userToAdjust)
                .text(commentDto.getText()).build();
        Comment savedComment = commentRepository.save(comment);
        return commentDto;
    }

    @Transactional
    public CommentDto deleteComment(long userId) {

        Comment comment = commentRepository.getById(userId);

        return CommentDto.builder().userId(comment.getUser().getId())
                .newsId(comment.getNews().getId())
                .text(comment.getText())
                .id(comment.getId())
                .build();
    }
}
