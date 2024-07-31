package com.qa.blog.mariadb.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity authorEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity categoryEntity;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 1024)
    private String content;

    @Column(name = "image")
    private String image;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tagEntities;

    public PostEntity() {}

    public PostEntity(Long id, AuthorEntity authorEntity, CategoryEntity categoryEntity, Set<TagEntity> tagEntities, String title, String content, String image) {
        this.id = id;
        this.authorEntity = authorEntity;
        this.categoryEntity = categoryEntity;
        this.tagEntities = tagEntities;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorEntity getAuthor() {
        return authorEntity;
    }

    public void setAuthor(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
    }

    public CategoryEntity getCategory() {
        return categoryEntity;
    }

    public void setCategory(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public Set<TagEntity> getTags() {
        return tagEntities;
    }

    public void setTags(Set<TagEntity> tagEntities) {
        this.tagEntities = tagEntities;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
