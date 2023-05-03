package com.gene.base.application.command.handler;

import com.gene.base.application.command.CreateBlogCommand;
import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.port.BlogRepository;
import io.jkratz.mediator.core.CommandHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

public class CreateBlogCommandHandler implements CommandHandler<CreateBlogCommand> {
    private final BlogRepository blogRepository;

    public CreateBlogCommandHandler(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void handle(@NotNull CreateBlogCommand createBlogCommand) {
        Blog newBlog = new Blog(createBlogCommand.getContent().toString(),
                createBlogCommand.getTitle(), createBlogCommand.getDescription(), createBlogCommand.getLocation());
        try {
            blogRepository.save(newBlog, true);
        } catch (DataAccessException | SQLException e) {
            throw new BlogServiceException("Error occurred while creating the blog! ", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
