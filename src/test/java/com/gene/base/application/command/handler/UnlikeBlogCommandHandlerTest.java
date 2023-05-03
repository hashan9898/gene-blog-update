package com.gene.base.application.command.handler;

import com.gene.base.application.command.UnlikeBlogCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.aggregrate.blogAggregrate.Like;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class UnlikeBlogCommandHandlerTest {
    @Mock
    private BlogRepository blogRepository;
    UnlikeBlogCommand unlikeBlogCommand = new UnlikeBlogCommand("blogId", "userId", "likeId");
    UnlikeBlogCommandHandler unlikeBlogCommandHandler;
    Blog blog;
    Like like;

    @BeforeEach
    void setUp() {
        unlikeBlogCommandHandler = new UnlikeBlogCommandHandler(blogRepository);
        blog = new Blog("blogId", "userId", "Blog Title", "location", null, new ArrayList<>());
        like = new Like("likeId", "userId", blog);
        blog.getLikes().add(like);
        when(blogRepository.findById(any())).thenReturn(blog);
    }

    @Test
    void handleSuccess() throws SQLException {
        unlikeBlogCommandHandler.handle(unlikeBlogCommand);
        Mockito.verify(blogRepository, Mockito.times(1)).addRemoveLike(blog, false);
    }

    @Test
    void handleInvalidUser() throws SQLException {
        blog.getLikes().remove(0);
        Like like = new Like("likeId", "userId123", blog);
        blog.getLikes().add(like);
        Mockito.doThrow(new SQLException()).when(blogRepository).addRemoveLike(any(), any());
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            unlikeBlogCommandHandler.handle(unlikeBlogCommand);
        });
        assertEquals("User ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    void handleException() throws SQLException {
        Mockito.doThrow(new SQLException()).when(blogRepository).addRemoveLike(any(), any());
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            unlikeBlogCommandHandler.handle(unlikeBlogCommand);
        });
        assertEquals("Error occurred while the unlike! ", exception.getErrorMessage());
    }
}