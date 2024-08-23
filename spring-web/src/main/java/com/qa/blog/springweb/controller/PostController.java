package com.qa.blog.springweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonPatch;
import com.qa.blog.core.domain.Author;
import com.qa.blog.core.domain.Category;
import com.qa.blog.core.domain.Post;
import com.qa.blog.core.domain.Tag;
import com.qa.blog.core.usecase.*;
import com.qa.blog.springweb.dto.PostDTO;
import com.qa.blog.springweb.dto.PostRequest;
import com.qa.blog.springweb.mapper.PostWebMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    private final CreatePost createPost;
    private final UpdatePost updatePost;
    private final FindPostById findPostById;
    private final SearchPosts searchPosts;
    private final DeletePostById deletePostById;
    private final PostWebMapper postWebMapper;
    private final ObjectMapper objectMapper;

    public PostController(CreatePost createPost, UpdatePost updatePost, FindPostById findPostById, SearchPosts searchPosts, DeletePostById deletePostById, PostWebMapper postWebMapper, ObjectMapper objectMapper) {
        this.createPost = createPost;
        this.updatePost = updatePost;
        this.findPostById = findPostById;
        this.searchPosts = searchPosts;
        this.deletePostById = deletePostById;
        this.postWebMapper = postWebMapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostRequest request) {
        Post post = createPost.execute(postWebMapper.toDomain(request));
        PostDTO response = postWebMapper.toDTO(post);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") Long id, @RequestBody JsonNode patch) {
        try {
            Post postToPatch = findPostById.execute(id);
            Post patchedPost = getPatchedPost(patch, postToPatch);
            Post updatedPost = updatePost.execute(patchedPost);
            PostDTO response = postWebMapper.toDTO(updatedPost);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> searchPosts(
        @RequestParam(name = "title", required = false) String title,
        @RequestParam(name = "category", required = false) String category,
        @RequestParam(name = "tags", required = false) String[] tags) {
        List<String> tagsList;
        if (tags != null) {
            tagsList = Arrays.asList(tags);
        } else {
            tagsList = Collections.emptyList();
        }
        List<Post> posts = searchPosts.execute(title, category, tagsList);
        List<PostDTO> postDTOs = posts.stream()
            .map(postWebMapper::toDTO)
            .toList();

        return ResponseEntity.ok(postDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable("id") Long id) {
        deletePostById.execute(id);
        return ResponseEntity.noContent().build();
    }

    private Post getPatchedPost(JsonNode patch, Post existingPost) throws JsonProcessingException {
        PostDTO existingPostToPatch = postWebMapper.toDTO(existingPost);
        JsonNode postNode = objectMapper.valueToTree(existingPostToPatch);
        JsonNode patchedNode = JsonPatch.apply(patch, postNode);
        PostDTO patchedPostDTO = objectMapper.treeToValue(patchedNode, PostDTO.class);
        Post patchedPostDomain = postWebMapper.toDomain(patchedPostDTO);
        return patchedPostWithIds(existingPost, patchedPostDomain) ;
    }

    private Post patchedPostWithIds(Post existingPost, Post patchedPostDomain) {
        Category category;
        Author author;
        if(existingPost.category().name().equals(patchedPostDomain.category().name())) {
            category = new Category(existingPost.category().id(), patchedPostDomain.category().name());

        }else{
            category = new Category(null, patchedPostDomain.category().name());
        }
        if(existingPost.author().name().equals(patchedPostDomain.author().name())) {
             author = new Author(existingPost.author().id(), patchedPostDomain.author().name());
        }else{
             author = new Author(null, patchedPostDomain.author().name());
        }
        Set<Tag> tags = patchedPostDomain.tags().stream().map(tag -> {
            Tag existingTag = existingPost.tags().stream()
                .filter(t -> t.name().equals(tag.name()))
                .findFirst()
                .orElse(null);
            if (existingTag != null) {
                return new Tag(existingTag.id(), tag.name());
            } else {
                return new Tag(null, tag.name());
            }
        }).collect(Collectors.toSet());

        return new Post(
            existingPost.id(),
            author,
            category,
            tags,
            patchedPostDomain.title(),
            patchedPostDomain.content(),
            patchedPostDomain.image()
        );
    }

}
