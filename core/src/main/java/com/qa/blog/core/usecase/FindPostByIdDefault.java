package com.qa.blog.core.usecase;

import com.qa.blog.core.domain.Post;
import com.qa.blog.core.PostRepository;

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
