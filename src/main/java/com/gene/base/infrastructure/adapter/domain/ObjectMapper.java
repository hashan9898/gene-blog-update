package com.gene.base.infrastructure.adapter.domain;

import com.gene.base.application.query.viewModel.BlogViewModel;
import com.gene.base.application.query.viewModel.CommentViewModel;
import com.gene.base.application.query.viewModel.LikeViewModel;
import com.gene.base.domain.aggregrate.blogAggregrate.Blog;
import com.gene.base.domain.aggregrate.blogAggregrate.Comment;
import com.gene.base.domain.aggregrate.blogAggregrate.Like;
import com.gene.base.infrastructure.entity.BlogDao;
import com.gene.base.infrastructure.entity.CommentDao;
import com.gene.base.infrastructure.entity.LikeDao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ObjectMapper {
    private final ModelMapper modelMapper;

    public ObjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<Like> likeDaoListToLikeList(List<LikeDao> likeDaos) {
        List<Like> likes = new ArrayList<>();
        likeDaos.stream().forEach(likeDao -> {
            likes.add(modelMapper.map(likeDao, Like.class));
        });
        return likes;
    }

    public List<Comment> commentDaoListToCommentList(List<CommentDao> commentDaos) {
        List<Comment> comments = new ArrayList<>();
        commentDaos.stream().forEach(commentDao -> {
            List<CommentDao> childCommentDos = commentDaos.stream()
                    .filter(childCommentDao -> childCommentDao.getParentCommentId() != null &&
                            childCommentDao.getParentCommentId().equals(commentDao.getId()))
                    .toList();
            Comment comment = modelMapper.map(commentDao, Comment.class);
            if (!childCommentDos.isEmpty()) {
                comment.setChildComments(commentDaoListToCommentList(childCommentDos));
            }
            if (commentDao.getParentCommentId() != null) {
                Optional<CommentDao> parentCommentDao = commentDaos
                        .stream()
                        .filter(commentParent -> commentParent.getId().equals(commentDao.getParentCommentId()))
                        .findFirst();
                comment.setParentComment(modelMapper.map(parentCommentDao, Comment.class));
            }
            comments.add(comment);
        });
        return comments;
    }

    public List<LikeDao> likeListToLikeDaoList(List<Like> likes) {
        List<LikeDao> likeDaos = new ArrayList<>();
        likes.stream().forEach(like -> {
            likeDaos.add(modelMapper.map(like, LikeDao.class));
        });
        return likeDaos;
    }

    public List<CommentDao> commentListToCommentDaoList(List<Comment> comments, Boolean isCommentAdd) {
        List<CommentDao> commentDaos = new ArrayList<>();
        AtomicReference<Optional<Comment>> newChildComment = new AtomicReference<>(Optional.empty()); // initialize to a non-null value
        comments.stream().forEach(comment -> {
            commentDaos.add(modelMapper.map(comment, CommentDao.class));
            if (isCommentAdd && comment.getChildComments() != null && newChildComment.get().isEmpty()) {
                newChildComment.set(comment.getChildComments()
                        .stream()
                        .filter(childComment -> childComment.getCreatedOn() == null)
                        .findFirst());
            }
        });
        if (newChildComment.get().isPresent()) {
            commentDaos.add(modelMapper.map(newChildComment.get().get(), CommentDao.class)); // retrieve the actual value from the Optional
        }
        return commentDaos;
    }

    public Blog blogDaoToBlog(BlogDao blogDao) {
        return modelMapper.map(blogDao, Blog.class);
    }

    public Comment commentDaoToComment(CommentDao commentDao) {
        return modelMapper.map(commentDao, Comment.class);
    }

    public BlogDao blogToBlogDao(Blog blog) {
        return modelMapper.map(blog, BlogDao.class);
    }

    public CommentDao commentToCommentDao(Comment comment) {
        return modelMapper.map(comment, CommentDao.class);
    }

    public LikeDao likeToLikeDao(Like like) {
        return modelMapper.map(like, LikeDao.class);
    }

    public List<CommentViewModel> commentDaoListToCommentViewModel(List<CommentDao> commentDaos) {
        List<CommentViewModel> commentViewModels = new ArrayList<>();
        commentDaos.forEach(commentDao -> {
            if (commentDao.getParentCommentId() == null) {
                CommentViewModel commentViewModel = modelMapper.map(commentDao, CommentViewModel.class);
                List<CommentDao> childCommentDos = commentDaos.stream()
                        .filter(childCommentDao -> childCommentDao.getParentCommentId() != null && childCommentDao.getParentCommentId().equals(commentDao.getId()))
                        .toList();
                List<CommentViewModel> childCommentViewModels = new ArrayList<>();
                childCommentDos.forEach(childCommentDao -> {
                    childCommentViewModels.add(modelMapper.map(childCommentDao, CommentViewModel.class));
                });
                commentViewModel.setChildComments(childCommentViewModels);
                commentViewModels.add(commentViewModel);
            }
        });
        return commentViewModels;
    }

    public List<LikeViewModel> likeDaoListToLikeViewModel(List<LikeDao> likeDaos) {
        List<LikeViewModel> likeViewModels = new ArrayList<>();
        likeDaos.forEach(commentDao -> {
            likeViewModels.add(modelMapper.map(commentDao, LikeViewModel.class));
        });
        return likeViewModels;
    }

    public BlogViewModel blogDaoToBlogViewModel(BlogDao blogDao) {
        return modelMapper.map(blogDao, BlogViewModel.class);
    }
}
