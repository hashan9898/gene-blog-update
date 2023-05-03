package com.gene.base.domain.aggregrate.blogAggregrate;

import com.gene.base.domain.aggregrate.LocalEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Like extends LocalEntity {
    private static final String LIKE_ID_PREFIX = "lid-";

    private String id;
    private String userId;
    private Blog blog;
    private Comment comment;

    public Like(String userId, Blog blog) {
        this.id = LIKE_ID_PREFIX + UUID.randomUUID();
        this.userId = userId;
        this.blog = blog;
    }

    public Like(String id, String userId, Blog blog) {
        this.id = id;
        this.userId = userId;
        this.blog = blog;
    }
}
