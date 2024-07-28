package com.qa.blog.core;

import java.util.List;

public interface SearchPosts {
    List<Post> execute(String title, String category, List<String> tags);
}
