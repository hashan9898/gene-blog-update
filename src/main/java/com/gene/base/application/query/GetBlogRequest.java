package com.gene.base.application.query;

import com.gene.base.application.query.viewModel.BlogViewModel;
import io.jkratz.mediator.core.Request;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBlogRequest implements Request<BlogViewModel> {
    private String blogId;

    public GetBlogRequest(String blogId) {
        this.blogId = blogId;
    }
}
