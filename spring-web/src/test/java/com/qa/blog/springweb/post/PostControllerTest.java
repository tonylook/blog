package com.qa.blog.springweb.post;

import com.qa.blog.core.*;
import com.qa.blog.core.exception.BlogException;
import com.qa.blog.springweb.PostController;
import com.qa.blog.springweb.PostMapper;
import com.qa.blog.springweb.PostResponse;
import com.qa.blog.springweb.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@EnableAutoConfiguration
@SpringBootTest(classes = {PostController.class, GlobalExceptionHandler.class})
class PostControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private CreatePost createPost;

    @MockBean
    private PostMapper postMapper;


    @Test
    void createPostSuccessfully() throws Exception {
        var postDomain = new Post(42L,
            new Author(41L, "Antonio Alvino"),
            new Category(33L, "Tech"),
            new ArrayList<>(List.of(
                new Tag(1L, "Java"),
                new Tag(2L, "Programming"))),
            "Titolo",
            "Contenuto 123 Prova",
            "http://example.com/image.jpg");
        var postResponse = new PostResponse(42L, "Antonio Alvino", "Tech",List.of("Java","Programming"),"Titolo","Contenuto 123 Prova","http://example.com/image.jpg");

        BDDMockito.given(createPost.execute(any()))
            .willReturn(postDomain);

        BDDMockito.given(postMapper.toDomain(any())).willReturn(postDomain);
        BDDMockito.given(postMapper.toResponse(postDomain)).willReturn(postResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "title": "Titolo",
                                "content": "Contenuto 123 Prova",
                                "author": "Antonio Alvino",
                                "image": "http://example.com/image.jpg",
                                "category": "Tech",
                                "tags": ["Java", "Programming"]
                            }
                            """)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(42L))
            .andExpect(jsonPath("$.title").value("Titolo"))
            .andExpect(jsonPath("$.content").value("Contenuto 123 Prova"))
            .andExpect(jsonPath("$.author").value("Antonio Alvino"))
            .andExpect(jsonPath("$.image").value("http://example.com/image.jpg"))
            .andExpect(jsonPath("$.category").value("Tech"))
            .andExpect(jsonPath("$.tags").isArray())
            .andExpect(jsonPath("$.tags[0]").value("Java"))
            .andExpect(jsonPath("$.tags[1]").value("Programming"));
    }

    @Test
    void createPostWithLongContent() throws Exception {
        String longContent = "a".repeat(1025);
        BDDMockito.given(postMapper.toDomain(any())).willThrow(new BlogException("BR-1","The content cannot be longer than 1024 characters"));
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                            {
                                "title": "Titolo",
                                "content": "%s",
                                "author": "Antonio Alvino",
                                "image": "http://example.com/image.jpg",
                                "category": "Tech",
                                "tags": ["Java", "Programming"]
                            }
                            """, longContent))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errorCode").value("BR-1"))
            .andExpect(jsonPath("$.errorMessage").value("The content cannot be longer than 1024 characters"));
    }
}
