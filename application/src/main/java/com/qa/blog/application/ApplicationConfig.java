package com.qa.blog.application;

import com.qa.blog.core.*;
import com.qa.blog.core.usecase.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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

    @Bean
    public DeletePostById deletePostById(PostRepository postRepository) {
        return new DeletePostByIdDefault(postRepository);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("QA Blog").version("undefined"));
    }
}
