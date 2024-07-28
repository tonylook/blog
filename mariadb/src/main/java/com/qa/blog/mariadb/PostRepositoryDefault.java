package com.qa.blog.mariadb;

import com.qa.blog.core.Post;
import com.qa.blog.core.PostRepository;
import com.qa.blog.core.exception.PostNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryDefault implements PostRepository {
    private final JPAPostRepository jpaPostRepository;
    private final PostEntityMapper postEntityMapper;
    private final CustomPostRepository customPostRepository;

    public PostRepositoryDefault(JPAPostRepository jpaPostRepository, PostEntityMapper postEntityMapper, CustomPostRepository customPostRepository) {
        this.jpaPostRepository = jpaPostRepository;
        this.postEntityMapper = postEntityMapper;
        this.customPostRepository = customPostRepository;
    }

    @Override
    public Post save(Post post) {
        return postEntityMapper.toDomain(jpaPostRepository.save(postEntityMapper.toEntity(post)));
    }

    @Override
    public Post findById(Long id) {
        PostEntity postEntity = jpaPostRepository.findById(id).orElseThrow(
            () -> new PostNotFoundException("Post with id: " + id + "Not Found"));
        return postEntityMapper.toDomain(postEntity);
    }

    @Override
    public List<Post> findByTitleAndCategoryAndTags(String title, String category, List<String> tags) {
        List<PostEntity> postEntities = customPostRepository.findByTitleAndCategoryAndTags(
            title == null ? "" : title,
            category == null ? "" : category,
            tags == null ? List.of() : tags
        );
        return postEntities.stream().map(postEntityMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaPostRepository.deleteById(id);
    }

}
