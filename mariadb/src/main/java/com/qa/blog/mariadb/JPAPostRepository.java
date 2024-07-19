package com.qa.blog.mariadb;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JPAPostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity save(PostEntity postEntity);
}
