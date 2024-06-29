package com.example.demo.aspects;

import com.example.demo.dto.NewsDto;
import com.example.demo.exceptions.NotAuthorizedException;
import com.example.demo.model.News;
import com.example.demo.repositories.NewsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;

@Component
@Aspect
public class PermissionAspect {

    static final String HEADER_NAME = "userid";

    @Autowired
    private NewsRepository newsRepository;

    @Before("@annotation(com.example.demo.annotations.RequireNewsAuthor)")
    public void checkNewsPermission(JoinPoint joinPoint) {
        final NewsDto newsDtoFromReq = extractNewsDtoFromArgs(joinPoint);
        final Long newsId = newsDtoFromReq.getId();
        final String desiredHeaderValue = extractHeaderValue(HEADER_NAME);

        validateNewsUpdatePermission(newsId, desiredHeaderValue);
    }

    private NewsDto extractNewsDtoFromArgs(JoinPoint joinPoint) {
        final Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof NewsDto) {
            return (NewsDto) args[0];
        }
        throw new IllegalArgumentException("Invalid argument: Expected NewsDto");
    }

    private void validateNewsUpdatePermission(Long newsId, String userIdFromHeader) {

        final Long userIdFromRequest = Long.parseLong(Objects.requireNonNull(userIdFromHeader));

        final Optional<News> newsOptional = newsRepository.findById(newsId);
        if (newsOptional.isPresent()) {
            final Long userIdFromNews = newsOptional
                .get()
                .getUser()
                .getId();
            
            if (!userIdFromNews.equals(userIdFromRequest)) {
                throw new NotAuthorizedException("We're sorry but news couldn't be updated by this user");
            }
        } else {
            throw new NotAuthorizedException("News not found");
        }
    }

    private String extractHeaderValue(String headerName) {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            final HttpServletRequest request = requestAttributes.getRequest();
            final Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                final String currentHeaderName = headerNames.nextElement();
                if (currentHeaderName.equalsIgnoreCase(headerName)) {
                    return request.getHeader(currentHeaderName);
                } else {
                    throw new IllegalStateException("UserId not found: " + headerName);
                }
            }
        }
        throw new IllegalStateException("Wrong HTTP reguest");
    }
}


