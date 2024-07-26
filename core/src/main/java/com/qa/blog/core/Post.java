package com.qa.blog.core;

import com.qa.blog.core.exception.BlogErrorCode;
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
            throw new BlogException (BlogErrorCode.CONTENT_TOO_LONG.getCode(), BlogErrorCode.CONTENT_TOO_LONG.getMessage());
        }
        if(title==null || title.isBlank()){
            throw new BlogException (BlogErrorCode.TITLE_REQUIRED.getCode(), BlogErrorCode.TITLE_REQUIRED.getMessage());
        }
    }
}
