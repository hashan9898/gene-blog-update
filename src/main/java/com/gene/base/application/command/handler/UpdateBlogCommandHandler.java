package com.gene.base.application.command.handler;

import com.gene.base.application.command.UpdateBlogCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.port.BlogRepository;
import io.jkratz.mediator.core.CommandHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

public class UpdateBlogCommandHandler implements CommandHandler<UpdateBlogCommand> {
    private final BlogRepository blogRepository;

    public UpdateBlogCommandHandler(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void handle(@NotNull UpdateBlogCommand updateBlogCommand) {
        Blog blog = blogRepository.findById(updateBlogCommand.getBlogId());
        if (blog == null) {
            throw new BlogServiceException("Blog ID is invalid! ", HttpStatus.BAD_REQUEST.value());
        } else if (!blog.getUserId().equals(updateBlogCommand.getUserId())) {
            throw new BlogServiceException("User ID is invalid! ", HttpStatus.FORBIDDEN.value());
        }
        blog.update(updateBlogCommand.getTitle(), updateBlogCommand.getContent(), updateBlogCommand.getDescription(), updateBlogCommand.getLocation());
        try {
            blogRepository.save(blog, false);
        } catch (DataAccessException | SQLException e) {
            throw new BlogServiceException("Error occurred while updating the blog! ", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
