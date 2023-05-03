package com.gene.base.application.query.viewModel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LikeViewModel {
    private String id;
    private Date createdOn;
    private Date updatedOn;
    private String userId;
}
