package com.campus.clean.arc.domain.author.usecases;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.exceptions.AuthorNotFoundException;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingleOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.foodtechlab.lib.auth.integration.core.credential.CredentialServiceFacade;
import ru.foodtechlab.lib.auth.service.facade.credential.dto.requests.ChangeCredentialPasswordRequest;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@RequiredArgsConstructor
public class ChangePasswordUseCase extends UseCase<ChangePasswordUseCase.InputValues, SingleOutput<Boolean>>{

    private final AuthorRepository repository;
    private final CredentialServiceFacade credentialFacade;


    @Override
    public SingleOutput<Boolean> execute(InputValues inputValues) {
        AuthorEntity entity = repository
                .findById(inputValues.id)
                .orElseThrow(AuthorNotFoundException::new);

        credentialFacade.changePassword(entity.getCredentialId(), ChangeCredentialPasswordRequest.of(inputValues.password));

        entity.setUpdatedAt(Instant.now());
        repository.save(entity);
        return SingleOutput.of(true);

    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor(staticName = "empty")
    @Builder
    public static class InputValues implements UseCase.InputValues {
        @NotBlank
        private String id;
        @NotBlank
        private String password;
    }
}
