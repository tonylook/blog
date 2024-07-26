package com.qa.blog.core;

public interface PostRepository {
    Post save(Post post);
    Post findById(Long id);
}
