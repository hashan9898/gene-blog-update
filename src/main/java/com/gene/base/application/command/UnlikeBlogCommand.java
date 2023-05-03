package com.gene.base.application.command;

import io.jkratz.mediator.core.Command;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnlikeBlogCommand implements Command {
    private String blogId;
    private String userId;
    private String likeId;

    public UnlikeBlogCommand(String blogId, String userId, String likeId) {
        this.blogId = blogId;
        this.userId = userId;
        this.likeId = likeId;
    }
}
