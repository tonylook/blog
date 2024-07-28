package com.qa.blog.core;

import java.util.List;

public class SearchPostsDefault implements SearchPosts {
    private final PostRepository postRepository;

    public SearchPostsDefault(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> execute(String title, String category, List<String> tags) {
        return postRepository.findByTitleAndCategoryAndTags(title, category, tags);
    }
}
