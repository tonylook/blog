package com.qa.blog.springweb.post;

import com.qa.blog.core.domain.Author;
import com.qa.blog.core.domain.Category;
import com.qa.blog.core.domain.Post;
import com.qa.blog.core.domain.Tag;
import com.qa.blog.core.exception.BlogException;
import com.qa.blog.springweb.mapper.PostWebMapper;
import com.qa.blog.springweb.dto.PostRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PostWebMapperTest {

    @Test
    void mapCreatePostRequestToDomain() {
        String title = "My First Post";
        String content = "This is the content";
        String authorName = "John Doe";
        String image = "http://example.com/image.jpg";
        String categoryName = "Tech";
        List<String> tags = List.of("Java", "Programming");

        PostRequest postRequest = new PostRequest(title, content, authorName, image, categoryName, tags);

        PostWebMapper underTest = new PostWebMapper();

        Post post = underTest.toDomain(postRequest);

        Assertions.assertThat(post.id()).isNull();
        Assertions.assertThat(post.title()).isEqualTo(title);
        Assertions.assertThat(post.content()).isEqualTo(content);
        Assertions.assertThat(post.author()).isEqualTo(new Author(null, authorName));
        Assertions.assertThat(post.image()).isEqualTo(image);
        Assertions.assertThat(post.category()).isEqualTo(new Category(null, categoryName));
        Assertions.assertThat(post.tags()).containsExactlyInAnyOrder(
            new Tag(null, "Java"),
            new Tag(null, "Programming")
        );
    }

    @Test
    void mapCreatePostRequestWithLongContentThrowsBlogException() {
        // Arrange
        String title = "My First Post";
        String content = "a".repeat(1025); // Content longer than 1024 characters
        String author = "John Doe";
        String image = "http://example.com/image.jpg";
        String category = "Tech";
        List<String> tags = List.of("Java", "Programming");

        PostRequest postRequest = new PostRequest(title, content, author, image, category, tags);

        PostWebMapper underTest = new PostWebMapper();

        // Act & Assert
        Assertions.assertThatThrownBy(() -> underTest.toDomain(postRequest))
            .isInstanceOf(BlogException.class)
            .hasMessageContaining("The content cannot be longer than 1024 characters");
    }
}