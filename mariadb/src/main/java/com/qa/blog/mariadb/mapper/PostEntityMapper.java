package com.qa.blog.mariadb.mapper;

import com.qa.blog.core.domain.Author;
import com.qa.blog.core.domain.Category;
import com.qa.blog.core.domain.Post;
import com.qa.blog.core.domain.Tag;
import com.qa.blog.mariadb.entity.AuthorEntity;
import com.qa.blog.mariadb.entity.CategoryEntity;
import com.qa.blog.mariadb.entity.PostEntity;
import com.qa.blog.mariadb.entity.TagEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class PostEntityMapper {
    public Post toDomain(PostEntity entity) {
        Author author = new Author(entity.getAuthor().getId(), entity.getAuthor().getName());
        Category category = new Category(entity.getCategory().getId(), entity.getCategory().getName());
        List<Tag> tags = entity.getTags().stream()
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
