package com.qa.blog.core.usecase;

import com.qa.blog.core.PostRepository;

public class DeletePostByIdDefault implements DeletePostById {
    private final PostRepository postRepository;

    public DeletePostByIdDefault(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void execute(Long id) {
        postRepository.deleteById(id);
    }
}
