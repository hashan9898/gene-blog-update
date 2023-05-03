package com.gene.base.application.command.handler;

import com.gene.base.application.command.UpdateBlogCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.port.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class UpdateBlogCommandHandlerTest {
    @Mock
    private BlogRepository blogRepository;
    UpdateBlogCommand updateBlogCommand = new UpdateBlogCommand("blogId", "userId", List.of("new content", "extended content"), "This is title", "This is desc", "This is location");
    UpdateBlogCommandHandler updateBlogCommandHandler;
    Blog blog;

    @BeforeEach
    void setUp() {
        updateBlogCommandHandler = new UpdateBlogCommandHandler(blogRepository);
        blog = new Blog("Test Content", "Test Title", "Test Description", "Test Location");
        blog.setId("blogId");
        when(blogRepository.findById("blogId")).thenReturn(blog);
    }

    @Test
    public void handleSuccess() throws SQLException {
        updateBlogCommandHandler.handle(updateBlogCommand);
        verify(blogRepository, times(1)).findById("blogId");
        verify(blogRepository, times(1)).save(blog, false);
    }

    @Test
    public void handleInvalidBlogId() {
        when(blogRepository.findById(any())).thenReturn(null);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            updateBlogCommandHandler.handle(updateBlogCommand);
        });
        assertEquals("Blog ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    public void handleInvalidUserId() {
        blog.setUserId("userId123");
        when(blogRepository.findById(any())).thenReturn(blog);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            updateBlogCommandHandler.handle(updateBlogCommand);
        });
        assertEquals("User ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    void handleException() throws SQLException {
        Mockito.doThrow(new SQLException()).when(blogRepository).save(any(), any());
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            updateBlogCommandHandler.handle(updateBlogCommand);
        });
        assertEquals("Error occurred while updating the blog! ", exception.getErrorMessage());
    }
}

