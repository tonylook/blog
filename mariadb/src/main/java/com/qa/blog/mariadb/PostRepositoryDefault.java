package com.qa.blog.mariadb;

import com.qa.blog.core.Post;
import com.qa.blog.core.PostRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryDefault implements PostRepository {
    private final JPAPostRepository jpaPostRepository;
    private final PostEntityMapper postEntityMapper;

    public PostRepositoryDefault(JPAPostRepository jpaPostRepository, PostEntityMapper postEntityMapper) {
        this.jpaPostRepository = jpaPostRepository;
        this.postEntityMapper = postEntityMapper;
    }

    @Override
    public Post save(Post post) {
        return postEntityMapper.toDomain(jpaPostRepository.save(postEntityMapper.toEntity(post)));
    }
}
