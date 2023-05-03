package com.gene.base.application.query.handler;

import com.gene.base.application.query.GetBlogsRequest;
import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.BlogViewModel;
import io.jkratz.mediator.core.RequestHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public class GetBlogsRequestHandler implements RequestHandler<GetBlogsRequest, Page<BlogViewModel>> {
    private final BlogQuery blogQuery;

    public GetBlogsRequestHandler(BlogQuery blogQuery) {
        this.blogQuery = blogQuery;
    }

    @Override
    public Page<BlogViewModel> handle(@NotNull GetBlogsRequest getBlogsRequest) {
        Pageable pageable = PageRequest.of(getBlogsRequest.getPage(), getBlogsRequest.getSize());
        Page<BlogViewModel> blogs = blogQuery.findAll(pageable);
        return blogs;
    }
}
