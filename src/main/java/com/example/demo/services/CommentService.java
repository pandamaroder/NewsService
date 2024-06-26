package com.example.demo.services;

import com.example.demo.dto.CommentCreateDto;
import com.example.demo.dto.CommentDto;
import com.example.demo.dto.CommentUpdateDto;
import com.example.demo.exceptions.NotFoundException;
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
    public CommentDto createComment(CommentCreateDto commentDto) {
        final User userToAdjust = userRepository.getReferenceById(commentDto.getUserId());
        final News newsToAdjust = newsRepository.getReferenceById(commentDto.getNewsId());
        final Comment comment = Comment.builder()
                .news(newsToAdjust)
                .user(userToAdjust)
                .text(commentDto.getText())
                .build();
        commentRepository.save(comment);
        return CommentDto.builder()
            .userId(comment.getUser().getId())
            .newsId(comment.getNews().getId())
            .text(comment.getText())
            .id(comment.getId())
            .build();
    }

    @Transactional
    public CommentDto deleteComment(long commentId) {

        final Comment comment = commentRepository.getById(commentId);

        return CommentDto.builder()
                .userId(comment.getUser().getId())
                .newsId(comment.getNews().getId())
                .text(comment.getText())
                .id(comment.getId())
                .build();
    }

    @Transactional
    public CommentDto updateComment(CommentUpdateDto commentCreateDto) {
        //TODO комментарии редактировать по счетчику
        final Comment commentToUpdate = commentRepository
                .findById(commentCreateDto.id())
                .orElseThrow(() -> new NotFoundException("Комментария с таким ID не существует"));
        commentToUpdate.setText(commentCreateDto.text());

        commentRepository.save(commentToUpdate);
        return CommentDto.builder().text(commentToUpdate.getText())
                .newsId(commentToUpdate.getNews().getId())
                .userId(commentToUpdate.getUser().getId())
                .build();
    }
}
