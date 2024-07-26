package com.qa.blog.core;

public class UpdatePostDefault implements UpdatePost {
    private final PostRepository postRepository;

    public UpdatePostDefault(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post execute(Post post) {
        return postRepository.save(post);
    }
}