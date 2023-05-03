package com.gene.base.domain.aggregrate;

import lombok.Data;

import java.util.Date;

@Data
public class LocalEntity {
    private Date createdOn;
    private Date updatedOn;

    //    TODO: add user here from the session
    private String userId = "userId";
}
