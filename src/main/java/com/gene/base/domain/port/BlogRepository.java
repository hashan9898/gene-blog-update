package com.gene.base.domain.port;

import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public interface BlogRepository {

    Blog findById(String blogId);

    Blog save(Blog blog, Boolean isCreate) throws SQLException;

    void delete(Blog blog) throws SQLException;

    void saveDeleteComment(Blog blog, Boolean isCreate) throws SQLException;

    void addRemoveLike(Blog blog, Boolean isAdd) throws SQLException;

}
