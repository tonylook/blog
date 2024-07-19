package com.qa.blog.springweb;

import com.qa.blog.core.Post;

public interface PostMapper {
    Post toDomain(PostRequest post);
    PostResponse toResponse(Post post);
}
