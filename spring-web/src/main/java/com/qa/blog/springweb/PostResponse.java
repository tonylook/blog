package com.qa.blog.springweb;

import java.util.List;

public record PostResponse(Long id, String author, String category, List<String> tags, String title,
                                 String content, String image) {
}
