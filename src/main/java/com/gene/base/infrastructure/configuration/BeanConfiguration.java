package com.gene.base.infrastructure.configuration;

import com.gene.base.BaseApplication;
import com.gene.base.application.command.handler.*;
import com.gene.base.application.query.handler.GetBlogByUserRequestHandler;
import com.gene.base.application.query.handler.GetBlogCommentsRequestHandler;
import com.gene.base.application.query.handler.GetBlogRequestHandler;
import com.gene.base.application.query.handler.GetBlogsRequestHandler;
import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.domain.port.BlogRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = BaseApplication.class)
public class BeanConfiguration {

    @Bean
    CreateBlogCommandHandler createBlogCommandHandler(final BlogRepository blogRepository) {
        return new CreateBlogCommandHandler(blogRepository);
    }

    @Bean
    UpdateBlogCommandHandler updateBlogCommandHandler(final BlogRepository blogRepository) {
        return new UpdateBlogCommandHandler(blogRepository);
    }

    @Bean
    DeleteBlogCommandHandler deleteBlogCommandHandler(final BlogRepository blogRepository) {
        return new DeleteBlogCommandHandler(blogRepository);
    }

    @Bean
    LikeBlogCommandHandler likeBlogCommandHandler(final BlogRepository blogRepository) {
        return new LikeBlogCommandHandler(blogRepository);
    }

    @Bean
    UnlikeBlogCommandHandler unlikeBlogCommandHandler(final BlogRepository blogRepository) {
        return new UnlikeBlogCommandHandler(blogRepository);
    }

    @Bean
    CreateCommentCommandHandler commentBlogCommandHandler(final BlogRepository blogRepository) {
        return new CreateCommentCommandHandler(blogRepository);
    }

    @Bean
    UpdateCommentCommandHandler updateCommentBlogCommandHandler(final BlogRepository blogRepository) {
        return new UpdateCommentCommandHandler(blogRepository);
    }

    @Bean
    DeleteCommentCommandHandler deleteCommentBlogCommandHandler(final BlogRepository blogRepository) {
        return new DeleteCommentCommandHandler(blogRepository);
    }

    @Bean
    GetBlogsRequestHandler getBlogsRequestHandler(final BlogQuery blogQuery) {
        return new GetBlogsRequestHandler(blogQuery);
    }

    @Bean
    GetBlogByUserRequestHandler getBlogByUserRequestHandler(final BlogQuery blogQuery) {
        return new GetBlogByUserRequestHandler(blogQuery);
    }

    @Bean
    GetBlogRequestHandler getBlogRequestHandler(final BlogQuery blogQuery) {
        return new GetBlogRequestHandler(blogQuery);
    }

    @Bean
    GetBlogCommentsRequestHandler getBlogCommentsRequestHandler(BlogQuery blogQuery){
        return new GetBlogCommentsRequestHandler(blogQuery);
    }
}

