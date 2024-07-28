package com.qa.blog.application;

import com.qa.blog.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {
    @Bean
    public CreatePost createPost(PostRepository postRepository) {
        return new CreatePostDefault(postRepository);
    }

    @Bean
    public UpdatePost updatePost(PostRepository postRepository) {
        return new UpdatePostDefault(postRepository);
    }

    @Bean
    public FindPostById findPost(PostRepository postRepository) {
        return new FindPostByIdDefault(postRepository);
    }

    @Bean
    public SearchPosts searchPosts(PostRepository postRepository) {
        return new SearchPostsDefault(postRepository);
    }
}
