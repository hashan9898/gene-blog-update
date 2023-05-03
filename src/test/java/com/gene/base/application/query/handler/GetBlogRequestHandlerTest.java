package com.gene.base.application.query.handler;

import com.gene.base.application.query.GetBlogRequest;
import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.BlogViewModel;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class GetBlogRequestHandlerTest {
    @Mock
    private BlogQuery blogQuery;
    GetBlogRequest request = new GetBlogRequest("blogId");

    @Test
    public void testHandle() {
        Blog testBlog = new Blog("Test Blog Content", "Test Blog Title", "Test Blog Description", "Test Blog Location");
        testBlog.setUserId("test_user_id");
        testBlog.setId("blogId");
        GetBlogRequestHandler getBlogRequestHandler = new GetBlogRequestHandler(blogQuery);
        when(blogQuery.findById(any())).thenReturn(new BlogViewModel("blogId", "userId", "Test Blog Content",
                "Test Blog Title", "Test Blog Description", "Test Blog Location", new Date(), new Date(), null, null));
        BlogViewModel result = getBlogRequestHandler.handle(request);
        assertEquals(testBlog.getId(), result.getId());
        assertEquals(testBlog.getTitle(), result.getTitle());
        assertNotNull(result);
    }
}