package com.qa.blog.springweb.dto;

import java.util.List;

public record PostDTO(Long id, String author, String category, List<String> tags, String title,
                      String content, String image) {
}
