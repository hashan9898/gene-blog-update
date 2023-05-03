package com.gene.base.application.command.handler;

import com.gene.base.application.command.CreateCommentCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.port.BlogRepository;
import io.jkratz.mediator.core.CommandHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

public class CreateCommentCommandHandler implements CommandHandler<CreateCommentCommand> {
    private final BlogRepository blogRepository;

    public CreateCommentCommandHandler(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void handle(@NotNull CreateCommentCommand createCommentCommand) {
        Blog blog = blogRepository.findById(createCommentCommand.getBlogId());
        if (blog == null) {
            throw new BlogServiceException("Blog ID is invalid! ", HttpStatus.BAD_REQUEST.value());
        }
        if (createCommentCommand.getParentCommentId() != null) {
            blog.addComment(createCommentCommand.getUserId(), createCommentCommand.getParentCommentId(), createCommentCommand.getCommentDesc());
        } else {
            blog.addComment(createCommentCommand.getUserId(), createCommentCommand.getCommentDesc());
        }
        try {
            blogRepository.saveDeleteComment(blog, true);
        } catch (DataAccessException |
                 SQLException e) {
            throw new BlogServiceException("Error occurred while creating the comment! ", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
