package com.gene.base.application.query.handler;

import com.gene.base.application.query.GetBlogCommentsRequest;
import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.CommentViewModel;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class GetBlogCommentsRequestHandlerTest {
    @Mock
    private BlogQuery blogQuery;
    GetBlogCommentsRequest request = new GetBlogCommentsRequest("blogId");

    @Test
    public void handle() {
        GetBlogCommentsRequestHandler getBlogCommentsRequestHandler = new GetBlogCommentsRequestHandler(blogQuery);
        Blog blog = new Blog("content", "title", "description", "location");
        blog.setId("blogId");
        List<CommentViewModel> comments = new ArrayList<>();
        comments.add(new CommentViewModel("commentId1", "userId1", "commentDesc1"));
        comments.add(new CommentViewModel("commentId2", "userId2", "commentDesc2"));
        when(blogQuery.findCommentsByBlogId("blogId")).thenReturn(comments);
        List<CommentViewModel> result = getBlogCommentsRequestHandler.handle(request);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}