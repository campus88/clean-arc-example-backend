package com.campus.clean.arc.api.author.v1.mapper;

import com.campus.clean.arc.api.author.v1.dto.request.AuthorEditRequest;
import com.campus.clean.arc.api.author.v1.dto.request.ChangePasswordRequest;
import com.campus.clean.arc.api.author.v1.dto.request.RegistrationRequest;
import com.campus.clean.arc.api.author.v1.dto.response.AuthorResponse;
import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.campus.clean.arc.domain.author.usecases.ChangePasswordUseCase;
import com.campus.clean.arc.domain.author.usecases.CreateAuthorUseCase;
import com.campus.clean.arc.domain.author.usecases.UpdateAuthorUseCase;
import com.rcore.rest.api.commons.request.SearchApiRequest;

public class AuthorMapper {

    public static UpdateAuthorUseCase.InputValues map(AuthorEditRequest request){
        return UpdateAuthorUseCase.InputValues.of(request.getId(), request.getFirstName(), request.getLastName());
    }

    public static ChangePasswordUseCase.InputValues map(String authorId, ChangePasswordRequest request){
        return ChangePasswordUseCase.InputValues.of(authorId, request.getPassword());
    }

    public static AuthorFilters map(SearchApiRequest request) {
        var filter = AuthorFilters.builder()
                .limit(request.getLimit())
                .offset(request.getOffset())
                .query(request.getQuery())
                .sortDirection(request.getSortDirection())
                .sortName(request.getSortName());

        return filter.build();
    }

    public static CreateAuthorUseCase.InputValues map(RegistrationRequest request) {
        var filter = CreateAuthorUseCase.InputValues.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName());

        return filter.build();
    }

    public static AuthorResponse map(AuthorEntity item) {
        return AuthorResponse.builder()
                .id(item.getId())
                .firstName(item.getFirstName())
                .lastName(item.getLastName())
                .email(item.getEmail())
                .build();
    }


}
