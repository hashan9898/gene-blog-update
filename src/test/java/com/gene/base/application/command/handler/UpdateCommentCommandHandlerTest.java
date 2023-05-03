package com.gene.base.application.command.handler;

import com.gene.base.application.command.UpdateCommentCommand;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class UpdateCommentCommandHandlerTest {
    @Mock
    private BlogRepository blogRepository;
    UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand("blogId", "userId",
            "commentId", null, "This is desc");
    UpdateCommentCommandHandler updateCommentCommandHandler;
    Blog blog;
    Comment comment;

    @BeforeEach
    void setUp() {
        updateCommentCommandHandler = new UpdateCommentCommandHandler(blogRepository);
        blog = new Blog("content", "title", "description", "location", new ArrayList<>(), null);
        blog.setId("blogId");
        comment = new Comment("userId", new Comment("parentCommentId"), "commentDesc", blog);
        comment.setId("commentId");
        blog.getComments().add(comment);
        Mockito.when(blogRepository.findById("blogId")).thenReturn(blog);
    }

    @Test
    void handle() throws SQLException {
        updateCommentCommandHandler.handle(updateCommentCommand);
        Mockito.verify(blogRepository).saveDeleteComment(blog, false);
        assertEquals(1, blog.getComments().size());
        Comment commentObj = blog.getComments().get(0);
        assertEquals("This is desc", commentObj.getCommentDesc());
    }

    @Test
    public void handleInvalidBlogId() {
        when(blogRepository.findById(any())).thenReturn(null);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> updateCommentCommandHandler.handle(updateCommentCommand));
        assertEquals("Blog ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    void handleException() throws SQLException {
        Mockito.doThrow(new SQLException()).when(blogRepository).saveDeleteComment(any(), any());
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            updateCommentCommandHandler.handle(updateCommentCommand);
        });
        assertEquals("Error occurred while updating the comment! ", exception.getErrorMessage());
    }

    @Test
    void handleInvalidUser() {
        blog.getComments().remove(0);
        Comment comment = new Comment("userId123", new Comment("parentCommentId"), "commentDesc", blog);
        comment.setId("commentId");
        blog.getComments().add(comment);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            updateCommentCommandHandler.handle(updateCommentCommand);
        });
        assertEquals("User ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    void handleInvalidCommentId() {
        blog.getComments().remove(0);
        Comment comment = new Comment("userId", new Comment("parentCommentId"), "commentDesc", blog);
        comment.setId("commentId123");
        blog.getComments().add(comment);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            updateCommentCommandHandler.handle(updateCommentCommand);
        });
        assertEquals("Comment ID is invalid! ", exception.getErrorMessage());
    }
}