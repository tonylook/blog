package com.qa.blog.springweb;

import java.util.List;

public record PostRequest(String title, String content, String author, String image, String category, List<String> tags) {
}
