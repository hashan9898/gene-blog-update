package com.gene.base.application.command.handler;

import com.gene.base.application.command.CreateBlogCommand;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class CreateBlogCommandHandlerTest {
    @Mock
    private BlogRepository blogRepository;
    CreateBlogCommand createBlogCommand = new CreateBlogCommand(List.of("new content", "extended content"), "This is title", "This is desc", "This is location");
    CreateBlogCommandHandler createBlogCommandHandler;

    @BeforeEach
    void setUp() {
        createBlogCommandHandler = new CreateBlogCommandHandler(blogRepository);
    }

    @Test
    void handleSuccess() throws SQLException {
        createBlogCommandHandler.handle(createBlogCommand);
        Mockito.verify(blogRepository).save(
                new Blog("[new content, extended content]", "This is title", "This is desc", "This is location", null, null), true);
    }

    @Test
    void handleException() throws SQLException {
        Mockito.when(blogRepository.save(any(), any())).thenThrow(new SQLException());
        BlogServiceException exception = assertThrows(BlogServiceException.class, () -> {
            createBlogCommandHandler.handle(createBlogCommand);
        });
        assertEquals("Error occurred while creating the blog! ", exception.getErrorMessage());
    }
}