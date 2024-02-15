package com.campus.clean.arc.api.article.v1.dto.request;

import lombok.Getter;

@Getter
public class ArticleCreateRequest {
    private String title;
    private String description;
}
