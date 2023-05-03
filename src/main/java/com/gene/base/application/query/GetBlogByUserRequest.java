package com.gene.base.application.query;

import com.gene.base.application.query.viewModel.BlogViewModel;
import io.jkratz.mediator.core.Request;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class GetBlogByUserRequest implements Request<Page<BlogViewModel>> {
    private Integer page;
    private Integer size;
    private String userId;

    public GetBlogByUserRequest(Integer page, Integer size, String userId) {
        this.page = page;
        this.size = size;
        this.userId = userId;
    }
}
