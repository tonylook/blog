package com.qa.blog.springweb;

import com.qa.blog.core.Author;
import com.qa.blog.core.Category;
import com.qa.blog.core.exception.BlogException;
import org.springframework.stereotype.Component;
import com.qa.blog.core.Post;
import com.qa.blog.core.Tag;

import java.util.List;

@Component
public class PostWebMapperDefault implements PostWebMapper {
    @Override
    public Post toDomain(PostRequest request) throws BlogException {
        Author author = new Author(null, request.author());
        Category category = new Category(null, request.category());
        List<Tag> tags = request.tags().stream()
            .map(tag -> new Tag(null, tag))
            .toList();
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

    @Override
    public PostResponse toResponse(Post post) {
        return new PostResponse(
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
