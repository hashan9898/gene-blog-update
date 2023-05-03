package com.gene.base.application.command.handler;

import com.gene.base.application.command.DeleteCommentCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.aggregrate.blogAggregrate.Comment;
import com.gene.base.domain.port.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class DeleteCommentCommandHandlerTest {
    @Mock
    private BlogRepository blogRepository;
    DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand("blogId", "userId", "commentId");
    Blog blog;
    DeleteCommentCommandHandler deleteCommentCommandHandler;

    @BeforeEach
    void setUp() {
        blog = new Blog("Content", "Title", "Description", "location", new ArrayList<>(), null);
        Comment comment = new Comment("commentId", "userId", "Comment desc", blog);
        blog.getComments().add(comment);
        when(blogRepository.findById(any())).thenReturn(blog);
        deleteCommentCommandHandler = new DeleteCommentCommandHandler(blogRepository);
    }

    @Test
    public void handleSuccess() throws Exception {
        deleteCommentCommandHandler.handle(deleteCommentCommand);
        verify(blogRepository).saveDeleteComment(blog, false);
        assertFalse(blog.getComments().stream()
                .filter(commentObj -> commentObj.getId().equals("commentId")).findFirst().isPresent());
    }

    @Test
    public void handleInvalidBlogId() {
        when(blogRepository.findById(any())).thenReturn(null);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> deleteCommentCommandHandler.handle(deleteCommentCommand));
        assertEquals("Blog ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    public void handleInvalidParentCommentId() {
        DeleteCommentCommand command = new DeleteCommentCommand("blogId", "invalidId", "commentId", "userId");
        Blog blog = new Blog("blogId", "userId", "Blog Title");
        blog.setComments(new ArrayList<>());
        Comment comment = new Comment("userId", new Comment("parentCommentId"), "commentDesc", blog);
        comment.setId("commentId");
        blog.getComments().add(comment);
        when(blogRepository.findById("blogId")).thenReturn(blog);
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> deleteCommentCommandHandler.handle(command));
        assertEquals("Parent Comment ID is invalid! ", exception.getErrorMessage());
    }

    @Test
    public void handleInvalidUserId() {
        DeleteCommentCommand command = new DeleteCommentCommand("blogId", "parentCommentId", "commentId", "invalidId");
        Blog blog = new Blog("blogId", "userId", "Blog Title");
        blog.setComments(new ArrayList<>());
        Comment comment = new Comment("userId", "commentDesc", blog);
        comment.setId("commentId");
        comment.setParentComment(new Comment("userId", "parentCommentDesc", blog));
        comment.getParentComment().setId("parentCommentId");
        blog.getComments().add(comment);
        when(blogRepository.findById("blogId")).thenReturn(blog);

        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> deleteCommentCommandHandler.handle(command));
        assertEquals("User ID is invalid! ", exception.getErrorMessage());
    }
}