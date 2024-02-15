package com.campus.clean.arc.domain.article.usecases;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.rcore.domain.commons.port.dto.SearchResult;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SearchResultEntityOutputValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
public class FindArticleByNameUseCase extends UseCase<FindArticleByNameUseCase.InputValues, SearchResultEntityOutputValues<ArticleEntity>> {

    private final ArticleRepository repository;

    @Override
    public SearchResultEntityOutputValues<ArticleEntity> execute(InputValues inputValues) {
        ArticleFilters articleFilters = ArticleFilters.builder()
                .limit(1000l)
                .offset(0l)
                .build();

        SearchResult<ArticleEntity> searchResult = repository.find(articleFilters);

        return SearchResultEntityOutputValues.of(searchResult);
    }

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class InputValues implements UseCase.InputValues {
        @NotBlank
        private String name;
    }
}