package com.gene.base.infrastructure.adapter.domain;

import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.port.BlogRepository;
import com.gene.base.infrastructure.entity.BlogDao;
import com.gene.base.infrastructure.entity.CommentDao;
import com.gene.base.infrastructure.repository.SpringDataMysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Component
public class MysqlDbBlogRepository implements BlogRepository {
    private final SpringDataMysqlBlogRepository springDataMysqlBlogRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public MysqlDbBlogRepository(final SpringDataMysqlBlogRepository springDataMysqlBlogRepository, ObjectMapper objectMapper) {
        this.springDataMysqlBlogRepository = springDataMysqlBlogRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Blog findById(String blogId) {
        Optional<BlogDao> blogDao = springDataMysqlBlogRepository.findById(blogId);
        Blog blog = blogDao.map(objectMapper::blogDaoToBlog).orElse(null);
        if (blogDao.isPresent() && blog != null) {
            blog.setComments(objectMapper.commentDaoListToCommentList(blogDao.get().getCommentDaos()));
            blog.setLikes(objectMapper.likeDaoListToLikeList(blogDao.get().getLikeDaos()));
        }
        return blog;
    }

    @Transactional
    @Override
    public Blog save(Blog blog, Boolean isCreate) throws SQLException {
        try {
            BlogDao blogDao = objectMapper.blogToBlogDao(blog);
            if (!isCreate) {
                blogDao.setLikeDaos(objectMapper.likeListToLikeDaoList(blog.getLikes()));
                blogDao.setCommentDaos(objectMapper.commentListToCommentDaoList(blog.getComments(), false));
            }
            springDataMysqlBlogRepository.save(blogDao);
            return objectMapper.blogDaoToBlog(blogDao);
        } catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void delete(Blog blog) throws SQLException {
        try {
            springDataMysqlBlogRepository.deleteById(blog.getId());
        } catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void saveDeleteComment(Blog blog, Boolean isCreated) throws SQLException {
        try {
            Optional<BlogDao> blogDao = springDataMysqlBlogRepository.findById(blog.getId());
            if (blogDao.isPresent()) {
                List<CommentDao> commentDaos = objectMapper.commentListToCommentDaoList(blog.getComments(), isCreated);
                blogDao.get().setCommentDaos(commentDaos);
                springDataMysqlBlogRepository.save(blogDao.get());
            }
        } catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public void addRemoveLike(Blog blog, Boolean isAdd) throws SQLException {
        Optional<BlogDao> blogDao = springDataMysqlBlogRepository.findById(blog.getId());
        if (blogDao.isPresent()) {
            blogDao.get().setLikeDaos(objectMapper.likeListToLikeDaoList(blog.getLikes()));
            try {
                springDataMysqlBlogRepository.save(blogDao.get());
            } catch (Exception e) {
                throw new SQLException(e.getMessage(), e);
            }
        }
    }
}
