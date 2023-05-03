package com.gene.base.application.query.handler;

import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.BlogViewModel;
import com.gene.base.application.query.GetBlogByUserRequest;
import io.jkratz.mediator.core.RequestHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public class GetBlogByUserRequestHandler implements RequestHandler<GetBlogByUserRequest, Page<BlogViewModel>> {
    private final BlogQuery blogQuery;

    public GetBlogByUserRequestHandler(BlogQuery blogQuery) {
        this.blogQuery = blogQuery;
    }

    @Override
    public Page<BlogViewModel> handle(@NotNull GetBlogByUserRequest getBlogByUserRequest) {
        Pageable pageable = PageRequest.of(getBlogByUserRequest.getPage(), getBlogByUserRequest.getSize());
        Page<BlogViewModel> blogs = blogQuery.findByUserId(getBlogByUserRequest.getUserId(), pageable);
        return blogs;
    }
}
