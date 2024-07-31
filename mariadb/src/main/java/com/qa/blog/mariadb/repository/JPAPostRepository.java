package com.qa.blog.mariadb.repository;

import com.qa.blog.mariadb.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface JPAPostRepository extends JpaRepository<PostEntity, Long> {
}
