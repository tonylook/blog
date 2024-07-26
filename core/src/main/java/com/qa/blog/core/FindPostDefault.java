package com.qa.blog.core;

public class FindPostDefault implements FindPost{
    private final PostRepository postRepository;

    public FindPostDefault(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post execute(Long id) {
        return postRepository.findById(id);
    }
}
