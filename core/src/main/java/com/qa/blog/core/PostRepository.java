package com.qa.blog.core;

import java.util.List;

public interface PostRepository {
    Post save(Post post);
    Post findById(Long id);
    List<Post> findByTitleAndCategoryAndTags (String title, String category, List<String> tags);
}
