package com.gene.base.application.command.handler;

import com.gene.base.application.command.DeleteCommentCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.port.BlogRepository;
import io.jkratz.mediator.core.CommandHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

public class DeleteCommentCommandHandler implements CommandHandler<DeleteCommentCommand> {
    private final BlogRepository blogRepository;

    public DeleteCommentCommandHandler(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void handle(@NotNull DeleteCommentCommand deleteCommentCommand) {
        Blog blog = blogRepository.findById(deleteCommentCommand.getBlogId());
        if (blog == null) {
            throw new BlogServiceException("Blog ID is invalid! ", HttpStatus.BAD_REQUEST.value());
        }
        if (deleteCommentCommand.getParentCommentId() != null) {
            blog.deleteComment(deleteCommentCommand.getCommentId(), deleteCommentCommand.getParentCommentId(), deleteCommentCommand.getUserId());
        } else {
            blog.deleteComment(deleteCommentCommand.getCommentId(), deleteCommentCommand.getUserId());
        }
        try {
            blogRepository.saveDeleteComment(blog, false);
        } catch (DataAccessException | SQLException e) {
            throw new BlogServiceException("Error occurred while deleting the comment! ", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
