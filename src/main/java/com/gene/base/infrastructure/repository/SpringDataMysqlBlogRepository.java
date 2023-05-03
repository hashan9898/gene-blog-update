package com.gene.base.infrastructure.repository;

import com.gene.base.infrastructure.entity.BlogDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMysqlBlogRepository extends JpaRepository<BlogDao, String> {
    @Query(value = "SELECT * FROM blog_dao b WHERE b.user_id=?1", nativeQuery = true)
    Page<BlogDao> findByUserId(String userId, Pageable pageable);
}
