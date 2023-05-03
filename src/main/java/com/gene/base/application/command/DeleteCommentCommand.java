package com.gene.base.application.command;

import io.jkratz.mediator.core.Command;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteCommentCommand implements Command {
    private String blogId;
    private String userId;
    private String commentId;
    private String parentCommentId;

    public DeleteCommentCommand(String blogId, String userId, String commentId) {
        this.blogId = blogId;
        this.userId = userId;
        this.commentId = commentId;
    }

    public DeleteCommentCommand(String blogId, String parentCommentId, String commentId, String userId) {
        this.blogId = blogId;
        this.userId = userId;
        this.commentId = commentId;
        this.parentCommentId = parentCommentId;
    }
}
