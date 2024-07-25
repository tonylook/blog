package com.qa.blog.core;

import com.qa.blog.core.exception.BlogException;

import java.util.List;

public record Post(Long id,
                   Author author,
                   Category category,
                   List<Tag> tags,
                   String title,
                   String content,
                   String image) {
    public Post{
        if(content.length()>1024){
            throw new BlogException ("BR-1","The content cannot be longer than 1024 characters");
        }
        if(title==null || title.isBlank()){
            throw new BlogException ("BR-2","Title is required");
        }
    }
}
