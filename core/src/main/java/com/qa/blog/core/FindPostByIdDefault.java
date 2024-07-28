package com.qa.blog.core;

public class FindPostByIdDefault implements FindPostById {
    private final PostRepository postRepository;

    public FindPostByIdDefault(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post execute(Long id) {
        return postRepository.findById(id);
    }
}
