package com.qa.blog.application;

import com.qa.blog.core.CreatePost;
import com.qa.blog.core.CreatePostDefault;
import com.qa.blog.core.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {
    @Bean
    public CreatePost createPost(PostRepository postRepository) {
        return new CreatePostDefault(postRepository);
    }
}
