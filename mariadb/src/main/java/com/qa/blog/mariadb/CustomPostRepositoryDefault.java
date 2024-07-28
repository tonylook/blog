package com.qa.blog.mariadb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CustomPostRepositoryDefault implements CustomPostRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PostEntity> findByTitleAndCategoryAndTags(String title, String category, List<String> tags) {
        StringBuilder queryString = new StringBuilder("SELECT p FROM PostEntity p LEFT JOIN p.categoryEntity c LEFT JOIN p.tagEntities t WHERE 1=1 ");

        if (title != null && !title.isEmpty()) {
            queryString.append("AND p.title LIKE :title ");
        }
        if (category != null && !category.isEmpty()) {
            queryString.append("AND c.name = :category ");
        }
        if (tags != null && !tags.isEmpty()) {
            queryString.append("AND t.name IN :tags ");
        }

        TypedQuery<PostEntity> query = entityManager.createQuery(queryString.toString(), PostEntity.class);

        if (title != null && !title.isEmpty()) {
            query.setParameter("title", "%" + title + "%");
        }
        if (category != null && !category.isEmpty()) {
            query.setParameter("category", category);
        }
        if (tags != null && !tags.isEmpty()) {
            query.setParameter("tags", tags);
        }

        return query.getResultList();
    }
}
