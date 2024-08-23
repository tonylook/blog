package com.qa.blog.springweb.mapper;

import com.qa.blog.core.domain.Author;
import com.qa.blog.core.domain.Category;
import com.qa.blog.core.exception.BlogException;
import com.qa.blog.springweb.dto.PostDTO;
import com.qa.blog.springweb.dto.PostRequest;
import org.springframework.stereotype.Component;
import com.qa.blog.core.domain.Post;
import com.qa.blog.core.domain.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PostWebMapper {
    public Post toDomain(PostRequest request) throws BlogException {
        Author author = new Author(null, request.author());
        Category category = new Category(null, request.category());
        Set<Tag> tags = request.tags().stream()
            .map(tag -> new Tag(null, tag))
            .collect(Collectors.toSet());
            return new Post(
                null,
                author,
                category,
                tags,
                request.title(),
                request.content(),
                request.image()
            );
    }

    public Post toDomain(PostDTO postDTO) throws BlogException {
        Author author = new Author(null, postDTO.author());
        Category category = new Category(null, postDTO.category());
        Set<Tag> tags = postDTO.tags().stream()
            .map(tag -> new Tag(null, tag))
            .collect(Collectors.toSet());
        return new Post(
            postDTO.id(),
            author,
            category,
            tags,
            postDTO.title(),
            postDTO.content(),
            postDTO.image()
        );
    }


    public PostDTO toDTO(Post post) {
        return new PostDTO(
            post.id(),
            post.author().name(),
            post.category().name(),
            post.tags().stream().map(Tag::name).toList(),
            post.title(),
            post.content(),
            post.image()
        );
    }
}
