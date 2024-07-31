package com.qa.blog.core.usecase;

import com.qa.blog.core.domain.Post;

import java.util.List;

public interface SearchPosts {
    List<Post> execute(String title, String category, List<String> tags);
}
