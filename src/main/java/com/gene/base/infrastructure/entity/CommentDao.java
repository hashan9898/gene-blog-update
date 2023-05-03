package com.gene.base.infrastructure.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class CommentDao extends BaseEntity {

    @Id
    private String id;
    private String parentCommentId;
    private String commentDesc;

    @ManyToOne
    @JoinColumn
    private BlogDao blogDao;

}
