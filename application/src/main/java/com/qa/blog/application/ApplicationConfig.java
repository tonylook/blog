package com.qa.blog.application;

import com.qa.blog.core.CreatePost;
import com.qa.blog.core.CreatePostDefault;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {
    @Bean
    public CreatePost createPost() {
        return new CreatePostDefault();
    }
}
