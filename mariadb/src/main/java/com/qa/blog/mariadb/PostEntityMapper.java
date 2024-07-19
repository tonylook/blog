package com.qa.blog.mariadb;

public interface PostEntityMapper {
    PostEntity toEntity(com.qa.blog.core.Post post);
    com.qa.blog.core.Post toDomain(PostEntity postEntity);
}
