package com.qa.blog.mariadb;

import com.qa.blog.core.Author;
import com.qa.blog.core.Category;
import com.qa.blog.core.Post;
import com.qa.blog.core.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class PostEntityMapperDefault implements PostEntityMapper {
    @Override
    public com.qa.blog.core.Post toDomain(PostEntity entity) {
        Author author = new Author(entity.getAuthor().getId(), entity.getAuthor().getName());
        Category category = new Category(entity.getCategory().getId(), entity.getCategory().getName());
        List<com.qa.blog.core.Tag> tags = entity.getTags().stream()
            .map(tagEntityEntity -> new Tag(tagEntityEntity.getId(), tagEntityEntity.getName()))
            .toList();

        return new Post(
            entity.getId(),
            author,
            category,
            tags,
            entity.getTitle(),
            entity.getContent(),
            entity.getImage()
        );
    }

    @Override
    public PostEntity toEntity(Post domain) {
        AuthorEntity authorEntity = new AuthorEntity(domain.author().id(), domain.author().name());
        CategoryEntity categoryEntity = new CategoryEntity(domain.category().id(), domain.category().name());
        Set<TagEntity> tagEntityEntities = domain.tags().stream()
            .map(tag -> new TagEntity(tag.id(), tag.name()))
            .collect(Collectors.toSet());

        return new PostEntity(
            domain.id(),
            authorEntity,
            categoryEntity,
            tagEntityEntities,
            domain.title(),
            domain.content(),
            domain.image()
        );
    }
}
