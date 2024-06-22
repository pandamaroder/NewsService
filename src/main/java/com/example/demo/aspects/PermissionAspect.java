package com.example.demo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PermissionAspect {

    /* @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;*/

    @Before("@annotation(com.example.demo.annotations.RequireNewsAuthor)")
    public void checkNewsPermission(JoinPoint joinPoint) {
        /*final Long newsId = (Long) joinPoint.getArgs()[0];

        Long commentId = null;
        final Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof NewsCreateRequest) {
            final NewsCreateRequest commentDto = (NewsCreateRequest) args[0];
            commentId = commentDto.;
        }
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = (HttpServletRequest)
            ((ServletRequestAttributes)
                Objects.requireNonNull(requestAttributes)).getRequest();

        final Optional<News> newsOptional = newsRepository.findById(newsId);
        if (newsOptional.isPresent()) {
            final News news = newsOptional.get();
            final User currentUser = userService.getCurrentUser(request);
            if (!news.getUser().equals(currentUser)) {
                throw new NotAuthorizedException("Вы не можете редактировать или удалять эту новость");
            }
        } else {
            throw new NotAuthorizedException("Новость не найдена");
        }*/
    }

    @Before("@annotation(com.example.demo.annotations.RequireCommentAuthor)")
    public void checkCommentPermission(JoinPoint joinPoint) {
    /*       Long commentId = null;
        final Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof CommentUpdateDto) {
            final CommentUpdateDto commentDto = (CommentUpdateDto) args[0];
            commentId = commentDto.id();
        }

        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        final HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) requestAttributes).getRequest();

        final Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            final Comment comment = commentOptional.get();
            final User currentUser = userService.getCurrentUser(request);
            if (!comment.getUser().equals(currentUser)) {
                throw new NotAuthorizedException("Вы не можете редактировать или удалять этот комментарий");
            }
        } else {
            throw new NotAuthorizedException("Комментарий не найден");
        }*/
    }
}


