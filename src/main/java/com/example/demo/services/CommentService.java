package com.example.demo.services;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
}
