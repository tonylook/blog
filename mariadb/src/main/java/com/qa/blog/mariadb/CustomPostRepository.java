package com.qa.blog.mariadb;

import java.util.List;

public interface CustomPostRepository {
    List<PostEntity> findByTitleAndCategoryAndTags(String title, String category, List<String> tags);
}