package com.gene.base.application.query.port;

import com.gene.base.application.query.viewModel.BlogViewModel;
import com.gene.base.application.query.viewModel.CommentViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlogQuery {
    Page<BlogViewModel> findByUserId(String userId, Pageable pageable);

    Page<BlogViewModel> findAll(Pageable pageable);

    BlogViewModel findById(String blogId);

    List<CommentViewModel> findCommentsByBlogId(String blogId);
}
