package com.example.demo.aspects;

import com.example.demo.dto.NewsDto;
import com.example.demo.exceptions.NotAuthorizedException;
import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repositories.NewsRepository;
import com.example.demo.repositories.UserRepository;
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

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Before("@annotation(com.example.demo.annotations.RequireNewsAuthor)")
    public void checkNewsPermission(JoinPoint joinPoint) {

        Long newsId = null;
        final String desiredHeaderName = "userid";
        String desiredHeaderValue = null;
        final Object[] args = joinPoint.getArgs();
        final NewsDto newsDtoFromReq = (NewsDto) args[0];
        if (args.length > 0 && args[0] instanceof NewsDto) {
            newsId = newsDtoFromReq.getId();
        }

        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        if (requestAttributes != null) {
            final HttpServletRequest request = requestAttributes.getRequest();
            final Enumeration<String> headerNames = request.getHeaderNames(); // Replace with the name of the header you're looking for

            while (headerNames.hasMoreElements()) {
                final String headerName = headerNames.nextElement();

                if (headerName.equalsIgnoreCase(desiredHeaderName)) {
                    desiredHeaderValue = request.getHeader(headerName);
                    break;
                }
            }
        }

        final Optional<News> newsOptional = newsRepository.findById(newsId);
        if (newsOptional.isPresent()) {
            final News newsFromApp = newsOptional.get();
            final Optional<User> userFromAppOptional = userRepository.findById(newsFromApp.getUser().getId());
            if (userFromAppOptional.isPresent() && !userFromAppOptional
                .get()
                .getId()
                .equals(Long.parseLong(Objects.requireNonNull(desiredHeaderValue)))) {
                throw new NotAuthorizedException("We're sorry but news couldn't be updated by user");
            }
        }
    }
}


