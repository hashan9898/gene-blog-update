package com.gene.base.application.query.handler;

import com.gene.base.application.query.GetBlogsRequest;
import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.BlogViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class GetBlogsRequestHandlerTest {
    @Mock
    private BlogQuery blogQuery;
    GetBlogsRequest request = new GetBlogsRequest(0, 10);

    @Test
    void handle() {
        Page<BlogViewModel> expectedBlogs = new PageImpl<>(List.of(new BlogViewModel("blogId", "userId", "content", "title", "description", "location", new Date(), new Date(), null, null)));
        when(blogQuery.findAll(any())).thenReturn(expectedBlogs);
        GetBlogsRequestHandler getBlogsRequestHandler = new GetBlogsRequestHandler(blogQuery);
        Page<BlogViewModel> result = getBlogsRequestHandler.handle(request);
        assertNotNull(result);
        assertEquals(expectedBlogs, result);
    }
}