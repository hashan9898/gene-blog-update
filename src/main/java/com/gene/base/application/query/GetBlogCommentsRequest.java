package com.gene.base.application.query;

import com.gene.base.application.query.viewModel.CommentViewModel;
import io.jkratz.mediator.core.Request;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetBlogCommentsRequest implements Request<List<CommentViewModel>> {
    private String blogId;

    public GetBlogCommentsRequest(String blogId) {
        this.blogId = blogId;
    }
}
