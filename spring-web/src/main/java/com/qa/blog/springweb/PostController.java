package com.qa.blog.springweb;
import com.qa.blog.core.CreatePost;
import com.qa.blog.core.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final CreatePost createPost;
    private final PostWebMapper postWebMapper;

    public PostController(CreatePost createPost, PostWebMapper postWebMapper) {
        this.createPost = createPost;
        this.postWebMapper = postWebMapper;
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
        Post post = createPost.execute(postWebMapper.toDomain(request));
        PostResponse response = postWebMapper.toResponse(post);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
