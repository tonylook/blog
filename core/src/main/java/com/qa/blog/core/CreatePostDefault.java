package com.qa.blog.core;

public class CreatePostDefault implements CreatePost {
    private final PostRepository postRepository;

    public CreatePostDefault(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post execute(Post post) {
        return postRepository.save(post);
    }
}