package com.gene.base.application.query.handler;

import com.gene.base.application.query.GetBlogCommentsRequest;
import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.CommentViewModel;
import io.jkratz.mediator.core.RequestHandler;

import javax.validation.constraints.NotNull;
import java.util.List;

public class GetBlogCommentsRequestHandler implements RequestHandler<GetBlogCommentsRequest, List<CommentViewModel>> {
    private final BlogQuery blogQuery;

    public GetBlogCommentsRequestHandler(BlogQuery blogQuery) {
        this.blogQuery = blogQuery;
    }

    @Override
    public List<CommentViewModel>  handle(@NotNull GetBlogCommentsRequest getBlogCommentsRequest) {
        List<CommentViewModel> comments = blogQuery.findCommentsByBlogId(getBlogCommentsRequest.getBlogId());
        return comments;
    }
}
