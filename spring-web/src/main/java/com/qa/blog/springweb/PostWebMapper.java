package com.qa.blog.springweb;

import com.qa.blog.core.Post;

public interface PostWebMapper {
    Post toDomain(PostRequest post);
    PostDTO toDTO(Post post);
    Post toDomain(PostDTO postDTO);
}
