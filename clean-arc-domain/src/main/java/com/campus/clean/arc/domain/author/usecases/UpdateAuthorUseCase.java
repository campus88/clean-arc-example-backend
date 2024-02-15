package com.campus.clean.arc.domain.author.usecases;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.exceptions.AuthorNotFoundException;
import com.campus.clean.arc.domain.author.port.AuthorIdGenerator;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonEntityOutputValues;
import lombok.*;
import ru.foodtechlab.lib.auth.integration.core.credential.CredentialServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.role.RoleServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.roleAccess.RoleAccessServiceFacade;

import java.time.Instant;

@RequiredArgsConstructor
public class UpdateAuthorUseCase  extends UseCase<UpdateAuthorUseCase.InputValues, SingletonEntityOutputValues<AuthorEntity>> {

    protected final AuthorRepository authorRepository;
    protected final AuthorIdGenerator idGenerator;
    protected final CredentialServiceFacade credentialFacade;
    protected final RoleServiceFacade roleFacade;
    protected final RoleAccessServiceFacade roleAccessServiceFacade;


    @Override
    public SingletonEntityOutputValues<AuthorEntity> execute(UpdateAuthorUseCase.InputValues inputValues) {
        AuthorEntity authorEntity = authorRepository.findById(inputValues.id).orElseThrow(AuthorNotFoundException::new);

        authorEntity.setFirstName(inputValues.firstName);
        authorEntity.setLastName(inputValues.lastName);
        authorEntity.setUpdatedAt(Instant.now());

        authorEntity = authorRepository.save(authorEntity);

        return SingletonEntityOutputValues.of(authorEntity);
    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Builder
    @Data
    public static class InputValues implements UseCase.InputValues {
        private String id;
        private String firstName;
        private String lastName;
    }
}
