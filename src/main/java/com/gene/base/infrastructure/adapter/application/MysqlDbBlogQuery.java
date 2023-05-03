package com.gene.base.infrastructure.adapter.application;

import com.gene.base.application.query.port.BlogQuery;
import com.gene.base.application.query.viewModel.BlogViewModel;
import com.gene.base.application.query.viewModel.CommentViewModel;
import com.gene.base.infrastructure.adapter.domain.ObjectMapper;
import com.gene.base.infrastructure.repository.SpringDataMysqlBlogRepository;
import com.gene.base.infrastructure.entity.BlogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Component
public class MysqlDbBlogQuery implements BlogQuery {
    private final SpringDataMysqlBlogRepository springDataMysqlBlogRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public MysqlDbBlogQuery(final SpringDataMysqlBlogRepository springDataMysqlBlogRepository, ObjectMapper objectMapper) {
        this.springDataMysqlBlogRepository = springDataMysqlBlogRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Page<BlogViewModel> findByUserId(String userId, Pageable pageable) {
        Page<BlogDao> blogs = springDataMysqlBlogRepository.findByUserId(userId, pageable);
        List<BlogViewModel> list = new ArrayList<>();

        blogs.toList().forEach(blog -> {
            BlogViewModel blogView = objectMapper.blogDaoToBlogViewModel(blog);
            blogView.setCommentViewModels(objectMapper.commentDaoListToCommentViewModel(blog.getCommentDaos()));
            blogView.setLikeViewModels(objectMapper.likeDaoListToLikeViewModel(blog.getLikeDaos()));
            list.add(blogView);
        });
        return new PageImpl<>(list);
    }

    @Override
    public Page<BlogViewModel> findAll(Pageable pageable) {
        Page<BlogDao> blogs = springDataMysqlBlogRepository.findAll(pageable);
        List<BlogViewModel> list = new ArrayList<>();

        blogs.toList().forEach(blog -> {
            BlogViewModel blogView = objectMapper.blogDaoToBlogViewModel(blog);
            blogView.setCommentViewModels(objectMapper.commentDaoListToCommentViewModel(blog.getCommentDaos()));
            blogView.setLikeViewModels(objectMapper.likeDaoListToLikeViewModel(blog.getLikeDaos()));
            list.add(blogView);
        });
        return new PageImpl<>(list);
    }

    @Override
    public BlogViewModel findById(String blogId) {
        Optional<BlogDao> blog = springDataMysqlBlogRepository.findById(blogId);
        if (blog.isPresent()) {
            BlogViewModel blogView = objectMapper.blogDaoToBlogViewModel(blog.get());
            blogView.setCommentViewModels(objectMapper.commentDaoListToCommentViewModel(blog.get().getCommentDaos()));
            blogView.setLikeViewModels(objectMapper.likeDaoListToLikeViewModel(blog.get().getLikeDaos()));
            return blogView;
        }
        return new BlogViewModel();
    }

    @Override
    public List<CommentViewModel> findCommentsByBlogId(String blogId) {
        Optional<BlogDao> blog = springDataMysqlBlogRepository.findById(blogId);
        if (blog.isPresent()) {
            return objectMapper.commentDaoListToCommentViewModel(blog.get().getCommentDaos());
        }
        return new ArrayList<>();
    }
}
