package com.gene.base.application.command.handler;

import com.gene.base.application.command.CreateCommentCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.aggregrate.blogAggregrate.Comment;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class CreateCommentCommandHandlerTest {
    @Mock
    private BlogRepository blogRepository;
    CreateCommentCommand createCommentCommand = new CreateCommentCommand("bid-24aa6166-401e-4145-aea8-fb495680b8b0",
            "user-123", null, "This is desc");
    CreateCommentCommandHandler createCommentCommandHandler;

    @BeforeEach
    void setUp() {
        Blog blog = new Blog("bid-5d92e250-ac34-44ae-8128-95935d8b2b4d", "[new content, extended content]",
                "This is title", "This is desc", "This is location", new ArrayList<>(), null);
        Mockito.when(blogRepository.findById(any())).thenReturn(blog);
        createCommentCommandHandler = new CreateCommentCommandHandler(blogRepository);
    }

    @Test
    void handleSuccess() throws SQLException {
        String blogId = "bid-" + UUID.randomUUID();
        Blog blog = new Blog("content", "title", "description", "location", new ArrayList<>(), null);
        blog.setId(blogId);
        Mockito.when(blogRepository.findById(blogId)).thenReturn(blog);
        CreateCommentCommand createCommentCommand = new CreateCommentCommand(
                blogId, "userId", null, "commentDesc");
        createCommentCommandHandler.handle(createCommentCommand);
        Mockito.verify(blogRepository).saveDeleteComment(blog, true);
        assertEquals(1, blog.getComments().size());
        Comment comment = blog.getComments().get(0);
        assertEquals("commentDesc", comment.getCommentDesc());
        assertEquals("userId", comment.getUserId());
    }

    @Test
    void handleBlogInvalid() {
        Mockito.when(blogRepository.findById(any())).thenReturn(null);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            createCommentCommandHandler.handle(createCommentCommand);
        });
        assertEquals("Blog ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    void handleException() throws SQLException {
        Mockito.doThrow(new SQLException()).when(blogRepository).saveDeleteComment(any(), any());
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            createCommentCommandHandler.handle(createCommentCommand);
        });
        assertEquals("Error occurred while creating the comment! ", exception.getErrorMessage());
    }
}
