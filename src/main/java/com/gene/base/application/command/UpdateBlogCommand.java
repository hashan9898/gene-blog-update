package com.gene.base.application.command;

import io.jkratz.mediator.core.Command;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateBlogCommand implements Command {

    private String blogId;

    private String userId;

    @NotNull(message = "Content should not be null!")
    @NotEmpty(message = "Content should not be empty!")
    private List<String> content;

    @NotNull(message = "Title should not be null!")
    @NotEmpty(message = "Title should not be empty!")
    @Size(max = 100, message = "Title should be max 100 characters!")
    private String title;

    @NotNull(message = "Description should not be null!")
    @NotEmpty(message = "Description should not be empty!")
    @Size(max = 225, message = "Description should be max 225 characters!")
    private String description;

    @NotNull(message = "Location should not be null!")
    @NotEmpty(message = "Location should not be empty!")
    @Size(max = 50, message = "Location should be max 50 characters!")
    private String location;

    public UpdateBlogCommand(String blogId, String userId, List<String> content, String title, String description, String location) {
        this.blogId = blogId;
        this.userId = userId;
        this.content = content;
        this.title = title;
        this.description = description;
        this.location = location;
    }
}
