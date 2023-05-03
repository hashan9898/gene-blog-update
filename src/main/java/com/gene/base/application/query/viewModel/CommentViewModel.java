package com.gene.base.application.query.viewModel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentViewModel {
    private String id;
    private List<CommentViewModel> childComments;
    private String commentDesc;
    private Date createdOn;
    private Date updatedOn;
    private String userId;

    public CommentViewModel(String id, String userId, String commentDesc) {
        this.id = id;
        this.commentDesc = commentDesc;
        this.userId = userId;
    }
}
