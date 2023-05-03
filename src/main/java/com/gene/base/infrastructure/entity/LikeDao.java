package com.gene.base.infrastructure.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class LikeDao extends BaseEntity {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn
    private BlogDao blogDao;
}
