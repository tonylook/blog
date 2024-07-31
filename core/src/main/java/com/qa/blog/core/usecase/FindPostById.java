package com.qa.blog.core.usecase;

import com.qa.blog.core.domain.Post;

public interface FindPostById {
    Post execute(Long id);
}
