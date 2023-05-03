package com.gene.base.application.query.handler;

import com.gene.base.application.query.GetBlogRequest;
import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.BlogViewModel;
import io.jkratz.mediator.core.RequestHandler;

import javax.validation.constraints.NotNull;

public class GetBlogRequestHandler implements RequestHandler<GetBlogRequest, BlogViewModel> {
    private final BlogQuery blogQuery;

    public GetBlogRequestHandler(BlogQuery blogQuery) {
        this.blogQuery = blogQuery;
    }

    @Override
    public BlogViewModel handle(@NotNull GetBlogRequest getBlogRequest) {
        BlogViewModel blog = blogQuery.findById(getBlogRequest.getBlogId());
        return blog;
    }
}
