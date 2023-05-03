package com.gene.base.application.command;

import io.jkratz.mediator.core.Command;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikeBlogCommand implements Command {
    private String blogId;
    private String userId;

    public LikeBlogCommand(String blogId, String userId) {
        this.blogId = blogId;
        this.userId = userId;
    }
}
