package com.qa.blog.core;

import com.qa.blog.core.domain.Post;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    Post findById(Long id);
    List<Post> findByTitleAndCategoryAndTags (String title, String category, List<String> tags);
    void deleteById(Long id);
}
