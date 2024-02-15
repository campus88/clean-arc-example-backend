package com.campus.clean.arc.api.article.v1.dto.request;

import lombok.Getter;

@Getter
public class ArticleUpdateRequest {
    private String id;
    private String title;
    private String description;
}
