package com.gene.base.application.command.handler;

import com.gene.base.application.command.LikeBlogCommand;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class LikeBlogCommandHandlerTest {
    @Mock
    private BlogRepository blogRepository;
    LikeBlogCommandHandler likeBlogCommandHandler;
    LikeBlogCommand likeBlogCommand = new LikeBlogCommand("blogId", "userId");
    Blog blog;

    @BeforeEach
    void setUp() {
        blog = new Blog("blogId", "userId", "Blog Title", "location",null, new ArrayList<>());
        when(blogRepository.findById("blogId")).thenReturn(blog);
        likeBlogCommandHandler = new LikeBlogCommandHandler(blogRepository);
    }

    @Test
    void handleSuccess() throws SQLException {
        likeBlogCommandHandler.handle(likeBlogCommand);
        assertEquals(1, blog.getLikes().size());
        assertTrue(blog.getLikes().get(0).getUserId().equals("userId"));
        verify(blogRepository, times(1)).addRemoveLike(blog, true);
    }

    @Test
    void handleInvalidBlogId() {
        when(blogRepository.findById(any())).thenReturn(null);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> likeBlogCommandHandler.handle(likeBlogCommand));
        assertEquals("Blog ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    void handleException() throws SQLException {
        Mockito.doThrow(new SQLException()).when(blogRepository).addRemoveLike(any(), any());
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            likeBlogCommandHandler.handle(likeBlogCommand);
        });
        assertEquals("Error occurred while the like! ", exception.getErrorMessage());
    }
}