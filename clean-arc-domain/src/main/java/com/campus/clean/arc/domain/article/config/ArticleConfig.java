package com.campus.clean.arc.domain.article.config;

import com.campus.clean.arc.domain.article.port.ArticleIdGenerator;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.campus.clean.arc.domain.article.usecases.*;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class ArticleConfig {

    private final CreateArticleUseCase createArticleUseCase;
    private final DeleteArticleUseCase deleteArticleUseCase;
    private final FindArticleByIdUseCase findArticleByIdUseCase;
    private final FindArticleByNameUseCase findArticleByNameUseCase;
    private final FindArticleWithFiltersUseCase findArticleWithFiltersUseCase;
    private final UpdateArticleUseCase updateArticleUseCase;


    public ArticleConfig(
            ArticleRepository repository,
            ArticleIdGenerator<?> idGenerator) {
        this.createArticleUseCase = new CreateArticleUseCase(idGenerator, repository);
        this.deleteArticleUseCase = new DeleteArticleUseCase(repository);
        this.findArticleByIdUseCase = new FindArticleByIdUseCase(repository);
        this.findArticleByNameUseCase = new FindArticleByNameUseCase(repository);
        this.findArticleWithFiltersUseCase = new FindArticleWithFiltersUseCase(repository);
        this.updateArticleUseCase = new UpdateArticleUseCase(repository);

    }
}