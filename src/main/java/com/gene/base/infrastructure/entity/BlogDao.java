package com.gene.base.infrastructure.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class BlogDao extends BaseEntity {
    @Id
    @Column(name = "blog_id")
    private String id;
    private String content;
    private String title;
    private String description;
    private String location;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "comment_dao")
    private List<CommentDao> commentDaos;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "like_dao")
    private List<LikeDao> likeDaos;

}
