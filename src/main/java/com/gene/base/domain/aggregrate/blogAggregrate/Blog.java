package com.gene.base.domain.aggregrate.blogAggregrate;

import com.gene.base.application.exception.BlogServiceException;
import com.gene.base.domain.aggregrate.AggregateRoot;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Blog extends AggregateRoot {
    private static final String BLOG_ID_PREFIX = "bid-";
    private static final String USER_ERROR = "User ID is invalid! ";
    private static String testId = null;

    private String id;
    private String content;
    private String title;
    private String description;
    private String location;
    private List<Comment> comments;
    private List<Like> likes;


    public Blog(String id, String userId, String title) {
        this.id = id;
        super.setUserId(userId);
        this.title = title;
    }

    public Blog(String content, String title, String description, String location) {
        super();
        this.id = BLOG_ID_PREFIX + UUID.randomUUID();
        testId = this.id;
        this.content = content;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public Blog(String content, String title, String description, String location, List<Comment> comments, List<Like> likes) {
        this.id = testId;
        this.content = content;
        this.title = title;
        this.description = description;
        this.location = location;
        this.comments = comments;
        this.likes = likes;
    }

    public Blog(String id, String content, String title, String description, String location, List<Comment> comments, List<Like> likes) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.description = description;
        this.location = location;
        this.comments = comments;
        this.likes = likes;
    }

    public Blog(String id) {
        this.id = id;
    }

    public void addComment(String userId, String parentCommentId, String commentDesc) {
        this.getComments().stream()
                .filter(comment -> comment.getId().equals(parentCommentId)).findFirst().get().addChildComment(userId, commentDesc);
    }

    public void addComment(String userId, String commentDesc) {
        this.getComments().add(new Comment(userId, commentDesc, this));
    }

    public void deleteComment(String commentId, String userId) {
        Comment comment = getComment(commentId);
        if (!comment.getUserId().equals(userId)) {
            throw new BlogServiceException(USER_ERROR, HttpStatus.FORBIDDEN.value());
        }
        this.getComments().remove(comment);
    }

    public void deleteComment(String commentId, String parentCommentId, String userId) {
        Comment comment = getComment(commentId);
        if (!parentCommentId.equals(comment.getParentComment().getId())) {
            throw new BlogServiceException("Parent Comment ID is invalid! ", HttpStatus.BAD_REQUEST.value());
        }
        if (!comment.getUserId().equals(userId)) {
            throw new BlogServiceException(USER_ERROR, HttpStatus.FORBIDDEN.value());
        }
        this.getComments().remove(comment);
    }

    public void addLike(String userId) {
        if (getLike(userId).isPresent()) {
            throw new BlogServiceException("User already liked the blog! ", HttpStatus.CONFLICT.value());
        }
        this.getLikes().add(new Like(userId, this));
    }

    public void unlike(String userId) {
        Like blogLike = getLike(userId)
                .orElseThrow(() -> new BlogServiceException(USER_ERROR, HttpStatus.CONFLICT.value()));
        this.getLikes().remove(blogLike);
    }

    public Optional<Like> getLike(String userId) {
        return this.getLikes()
                .stream()
                .filter(like -> like.getUserId().equals(userId))
                .findFirst();
    }

    public void update(String title, List<String> content, String description, String location) {
        this.setContent(content.toString());
        this.setDescription(description);
        this.setTitle(title);
        this.setLocation(location);
    }

    public void updateComment(String commentId, String description, String userId) {
        Comment comment = getComment(commentId);
        updateComment(comment, description, userId);
    }

    public void updateComment(Comment comment, String description, String userId) {
        if (!comment.getUserId().equals(userId)) {
            throw new BlogServiceException(USER_ERROR, HttpStatus.FORBIDDEN.value());
        }
        this.getComments().forEach(commentUpdate -> {
            if (commentUpdate.getId().equals(comment.getId())) {
                commentUpdate.setCommentDesc(description);
            }
        });
    }

    public void updateComment(String childCommentId, String parentCommentId, String commentDesc, String userId) {
        Comment parentComment = getComment(parentCommentId);
        if (!parentCommentId.equals(parentComment.getId())) {
            throw new BlogServiceException("Parent Comment ID is invalid! ", HttpStatus.BAD_REQUEST.value());
        }
        Comment childComment = parentComment.getChildComments()
                .stream()
                .filter(comment -> comment.getId().equals(childCommentId))
                .findFirst()
                .orElseThrow(() -> new BlogServiceException("Child Comment ID is invalid! ", HttpStatus.BAD_REQUEST.value()));
        updateComment(childComment, commentDesc, userId);
    }

    public Comment getComment(String commentId) {
        return this.getComments()
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new BlogServiceException("Comment ID is invalid! ", HttpStatus.BAD_REQUEST.value()));
    }
}
