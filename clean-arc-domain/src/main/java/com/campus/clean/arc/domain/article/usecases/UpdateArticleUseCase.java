package com.campus.clean.arc.domain.article.usecases;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.exceptions.ArticleNotFoundException;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonEntityOutputValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class UpdateArticleUseCase extends UseCase<UpdateArticleUseCase.InputValues, SingletonEntityOutputValues<ArticleEntity>> {

    private final ArticleRepository repository;

    @Override
    public SingletonEntityOutputValues<ArticleEntity> execute(UpdateArticleUseCase.InputValues inputValues) {
        Optional<ArticleEntity> optionalArticleEntity = repository.findById(inputValues.getId());

        if (optionalArticleEntity.isEmpty()) throw new ArticleNotFoundException();

        ArticleEntity articleEntity = optionalArticleEntity.get();
        articleEntity.setTitle(inputValues.title);
        articleEntity.setDescription(inputValues.description);
        articleEntity.setUpdatedAt(Instant.now());

        articleEntity = repository.save(articleEntity);
        return SingletonEntityOutputValues.of(articleEntity);
    }

    @AllArgsConstructor(staticName = "of")
    @Getter
    @Setter
    public static class InputValues implements UseCase.InputValues {
        private String id;
        private String title;
        private String description;
    }
}