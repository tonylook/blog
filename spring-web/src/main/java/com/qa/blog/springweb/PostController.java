package com.qa.blog.springweb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonPatch;
import com.qa.blog.core.CreatePost;
import com.qa.blog.core.FindPost;
import com.qa.blog.core.Post;
import com.qa.blog.core.UpdatePost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    private final CreatePost createPost;
    private final UpdatePost updatePost;
    private final FindPost findPost;
    private final PostWebMapper postWebMapper;
    private final ObjectMapper objectMapper;

    public PostController(CreatePost createPost, UpdatePost updatePost, FindPost findPost, PostWebMapper postWebMapper, ObjectMapper objectMapper) {
        this.createPost = createPost;
        this.updatePost = updatePost;
        this.findPost = findPost;
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
            Post postToPatch = findPost.execute(id);
            Post patchedPost = getPatchedPost(patch, postToPatch);
            Post updatedPost = updatePost.execute(patchedPost);
            PostDTO response = postWebMapper.toDTO(updatedPost);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Post getPatchedPost(JsonNode patch, Post existingPost) throws JsonProcessingException {
        PostDTO existingPostToPatch = postWebMapper.toDTO(existingPost);
        JsonNode postNode = objectMapper.valueToTree(existingPostToPatch);
        JsonNode patchedNode = JsonPatch.apply(patch, postNode);
        PostDTO patchedPostDTO = objectMapper.treeToValue(patchedNode, PostDTO.class);
        return postWebMapper.toDomain(patchedPostDTO);
    }

}
