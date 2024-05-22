package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseContextTest extends DemoApplicationBaseConfigTests {
    @Autowired
    private ApplicationContext applicationContext;
    @Test
    void contextLoads() {
        assertThat(applicationContext.getBeanDefinitionNames())
                .hasSizeGreaterThan(10);
    }
}
