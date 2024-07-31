package com.qa.blog.application.steps;

import com.qa.blog.mariadb.entity.PostEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;

    @Component
    public class ResultComponent {
        public WebTestClient.ResponseSpec actualResponse;
        public PostEntity post;
}
