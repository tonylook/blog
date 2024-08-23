package com.qa.blog.core.usecase;

import com.qa.blog.core.domain.Post;

public interface CreatePost {
    Post execute(Post post);
}
