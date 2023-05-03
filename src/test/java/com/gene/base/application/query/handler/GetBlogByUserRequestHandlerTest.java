package com.gene.base.application.query.handler;

import com.gene.base.application.query.GetBlogByUserRequest;
import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.BlogViewModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class GetBlogByUserRequestHandlerTest {
    @Mock
    private BlogQuery blogQuery;
    GetBlogByUserRequest request = new GetBlogByUserRequest(0, 10, "userId");
    Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

    @Test
    public void handle() {
        Page<BlogViewModel> expectedBlogs = new PageImpl<>(List.of(new BlogViewModel("blogId", "userId", "content", "title", "description", "location", new Date(), new Date(), null, null)));
        when(blogQuery.findByUserId(request.getUserId(), pageable)).thenReturn(expectedBlogs);
        GetBlogByUserRequestHandler getBlogByUserRequestHandler = new GetBlogByUserRequestHandler(blogQuery);
        Page<BlogViewModel> actualBlogs = getBlogByUserRequestHandler.handle(request);
        assertEquals(expectedBlogs, actualBlogs);
        verify(blogQuery, times(1)).findByUserId(request.getUserId(), pageable);
    }
}