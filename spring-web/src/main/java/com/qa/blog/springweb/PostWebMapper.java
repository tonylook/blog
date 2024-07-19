package com.qa.blog.springweb;

import com.qa.blog.core.Post;

public interface PostWebMapper {
    Post toDomain(PostRequest post);
    PostResponse toResponse(Post post);
}
