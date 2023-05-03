package com.gene.base.domain.aggregrate.blogAggregrate;

import com.gene.base.domain.aggregrate.LocalEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Comment extends LocalEntity {
    private static final String COMMENT_ID_PREFIX = "cid-";

    private String id;
    private String userId;
    private Comment parentComment;
    private String commentDesc;
    private Blog blog;
    private List<Comment> childComments;
    private List<Like> likes;


    public Comment(String id) {
        this.id = id;
    }

    public Comment(String userId, Comment parentComment, String commentDesc, Blog blog) {
        this.id = COMMENT_ID_PREFIX + UUID.randomUUID();
        this.userId = userId;
        this.parentComment = parentComment;
        this.commentDesc = commentDesc;
        this.blog = blog;
    }

    public Comment(String userId, String commentDesc, Blog blog) {
        this.id = COMMENT_ID_PREFIX + UUID.randomUUID();
        this.userId = userId;
        this.commentDesc = commentDesc;
        this.blog = blog;
    }

    public Comment(String id, String userId, String commentDesc, Blog blog) {
        this.id = id;
        this.userId = userId;
        this.commentDesc = commentDesc;
        this.blog = blog;
    }

    public void addChildComment(String userId, String commentDesc) {
        if (this.getChildComments() == null) {
            this.setChildComments(new ArrayList<>());
        }
        this.getChildComments().add(new Comment(userId, this, commentDesc, this.getBlog()));
    }
}
