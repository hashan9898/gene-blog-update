package com.gene.base.application.command.handler;

import com.gene.base.application.command.UpdateCommentCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.port.BlogRepository;
import io.jkratz.mediator.core.CommandHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

public class UpdateCommentCommandHandler implements CommandHandler<UpdateCommentCommand> {
    private final BlogRepository blogRepository;

    public UpdateCommentCommandHandler(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void handle(@NotNull UpdateCommentCommand updateCommentCommand) {
        Blog blog = blogRepository.findById(updateCommentCommand.getBlogId());
        if (blog == null) {
            throw new BlogServiceException("Blog ID is invalid! ", HttpStatus.BAD_REQUEST.value());
        }
        if (updateCommentCommand.getParentCommentId() != null) {
            blog.updateComment(updateCommentCommand.getCommentId(), updateCommentCommand.getParentCommentId(), updateCommentCommand.getCommentDesc(), updateCommentCommand.getUserId());
        } else {
            blog.updateComment(updateCommentCommand.getCommentId(), updateCommentCommand.getCommentDesc(), updateCommentCommand.getUserId());
        }
        try {
            blogRepository.saveDeleteComment(blog, false);
        } catch (DataAccessException | SQLException e) {
            throw new BlogServiceException("Error occurred while updating the comment! ", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
