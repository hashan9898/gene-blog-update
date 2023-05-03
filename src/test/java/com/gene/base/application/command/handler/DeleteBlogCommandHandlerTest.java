package com.gene.base.application.command.handler;

import com.gene.base.application.command.DeleteBlogCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.port.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class DeleteBlogCommandHandlerTest {
    @Mock
    private BlogRepository blogRepository;
    DeleteBlogCommand deleteBlogCommand = new DeleteBlogCommand("blogId", "userId");
    Blog blog;
    DeleteBlogCommandHandler deleteBlogCommandHandler;

    @BeforeEach
    void setUp() {
        blog = new Blog("content", "title", "description", "location");
        blog.setId("blogId");
        blog.setUserId("userId");
        when(blogRepository.findById(any())).thenReturn(blog);
        deleteBlogCommandHandler = new DeleteBlogCommandHandler(blogRepository);
    }

    @Test
    void handleSuccess() throws Exception {
        deleteBlogCommandHandler.handle(deleteBlogCommand);
        verify(blogRepository, times(1)).delete(blog);
    }

    @Test
    void handleInvalidBlogId() {
        when(blogRepository.findById(any())).thenReturn(null);
        assertThrows(BlogServiceException.class, () -> {
            deleteBlogCommandHandler.handle(deleteBlogCommand);
        });
    }

    @Test
    void handleInvalidUserId() {
        blog.setUserId("invalidUser");
        assertThrows(BlogServiceException.class, () -> {
            deleteBlogCommandHandler.handle(deleteBlogCommand);
        });
    }

    @Test
    void handleErrorDeletingBlog() throws Exception {
        doThrow(new SQLException()).when(blogRepository).delete(blog);
        assertThrows(BlogServiceException.class, () -> {
            deleteBlogCommandHandler.handle(deleteBlogCommand);
        });
    }
}