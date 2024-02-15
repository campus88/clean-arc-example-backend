package com.campus.clean.arc.api.article.v1.controller;


import com.campus.clean.arc.api.article.v1.dto.request.ArticleCreateRequest;
import com.campus.clean.arc.api.article.v1.dto.request.ArticleUpdateRequest;
import com.campus.clean.arc.api.article.v1.dto.response.ArticleResponse;
import com.campus.clean.arc.api.article.v1.mapper.ArticleMapper;
import com.campus.clean.arc.api.article.v1.routes.ArticleRoutes;
import com.campus.clean.arc.domain.article.config.ArticleConfig;
import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.exceptions.ArticleNotFoundException;
import com.rcore.domain.commons.usecase.UseCaseExecutor;
import com.rcore.domain.commons.usecase.model.FiltersInputValues;
import com.rcore.domain.commons.usecase.model.IdInputValues;
import com.rcore.rest.api.commons.request.SearchApiRequest;
import com.rcore.rest.api.commons.response.SearchApiResponse;
import com.rcore.rest.api.commons.response.SuccessApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Article API")
public class ArticleApiController {

    private final UseCaseExecutor useCaseExecutor;
    private final ArticleConfig articleConfig;

    @Operation(summary = "Search articles")
    @GetMapping(value = ArticleRoutes.ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<SearchApiResponse<ArticleResponse>> search(@ModelAttribute SearchApiRequest request) {
        return useCaseExecutor.execute(
                articleConfig.findArticleWithFiltersUseCase(),
                FiltersInputValues.of(ArticleMapper.map(request)),
                o -> SuccessApiResponse.of(SearchApiResponse.withItemsAndCount(
                        o.getResult().getItems()
                                .stream()
                                .map(entity -> ArticleMapper.map(entity))
                                .collect(Collectors.toList()),
                        o.getResult().getCount()
                ))
        );
    }

    @Operation(summary = "Get Article by id")
    @GetMapping(value = ArticleRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> findById(@PathVariable("id") String id) {
        Optional<ArticleEntity> articleEntity = useCaseExecutor.execute(
                articleConfig.findArticleByIdUseCase(),
                IdInputValues.of(id),
                o -> o.getEntity());

        return SuccessApiResponse.of(ArticleMapper.map(articleEntity.orElseThrow(ArticleNotFoundException::new)));
    }

    @Operation(summary = "Create Article")
    @PostMapping(value = ArticleRoutes.ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> create(@RequestBody ArticleCreateRequest request) {
        return useCaseExecutor.execute(
                articleConfig.createArticleUseCase(),
                ArticleMapper.map(request),
                o -> SuccessApiResponse.of(ArticleMapper.map(o.getEntity()))
        );
    }

    @Operation(summary = "Update Article")
    @PutMapping(value = ArticleRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> update(@PathVariable("id") String id, @RequestBody ArticleUpdateRequest request) {
        return useCaseExecutor.execute(
                articleConfig.updateArticleUseCase(),
                ArticleMapper.map(request),
                o -> SuccessApiResponse.of(ArticleMapper.map(o.getEntity()))
        );
    }

    @Operation(summary = "Delete Article")
    @DeleteMapping(value = ArticleRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<String> delete(@PathVariable("id") String id) {
        return useCaseExecutor.execute(
                articleConfig.deleteArticleUseCase(),
                IdInputValues.of(id),
                o -> SuccessApiResponse.of(HttpStatus.OK.name())
        );
    }




}
