package com.gene.base.application.query;

import com.gene.base.application.query.viewModel.BlogViewModel;
import io.jkratz.mediator.core.Request;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class GetBlogsRequest implements Request<Page<BlogViewModel>> {
    private Integer page;
    private Integer size;

    public GetBlogsRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}
