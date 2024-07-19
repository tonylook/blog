package com.qa.blog.springweb;
import com.qa.blog.core.CreatePost;
import com.qa.blog.core.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final CreatePost createPost;
    private final PostMapper postMapper;

    public PostController(CreatePost createPost, PostMapper postMapper) {
        this.createPost = createPost;
        this.postMapper = postMapper;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
        Post post = createPost.execute(postMapper.toDomain(request));
        PostResponse response = postMapper.toResponse(post);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
