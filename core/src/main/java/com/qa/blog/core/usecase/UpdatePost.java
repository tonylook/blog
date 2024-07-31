package com.qa.blog.core.usecase;

import com.qa.blog.core.domain.Post;

public interface UpdatePost {
    Post execute(Post post);
}