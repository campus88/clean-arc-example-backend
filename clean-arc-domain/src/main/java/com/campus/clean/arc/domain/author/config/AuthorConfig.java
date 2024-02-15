package com.campus.clean.arc.domain.author.config;

import com.campus.clean.arc.domain.author.port.AuthorIdGenerator;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.campus.clean.arc.domain.author.usecases.*;
import lombok.Getter;
import lombok.experimental.Accessors;
import ru.foodtechlab.lib.auth.integration.core.credential.CredentialServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.role.RoleServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.roleAccess.RoleAccessServiceFacade;

@Accessors(fluent = true)
@Getter
public class AuthorConfig {

    private final ChangePasswordUseCase changePasswordUseCase;
    private final CreateAuthorUseCase createAuthorUseCase;
    private final DeleteAuthorUseCase deleteAuthorUseCase;
    private final FindAuthorByCredentialIdUseCase findAuthorByCredentialIdUseCase;
    private final FindAuthorByIdUseCase findAuthorByIdUseCase;
    private final FindAuthorWithFiltersUseCase findAuthorWithFiltersUseCase;
    private final UpdateAuthorUseCase updateAuthorUseCase;

    public AuthorConfig(CredentialServiceFacade credentialFacade,
                        RoleServiceFacade roleFacade,
                        RoleAccessServiceFacade roleAccessServiceFacade,
                        AuthorRepository authorRepository,
                        AuthorIdGenerator<?> authorIdGenerator) {

        this.changePasswordUseCase = new ChangePasswordUseCase(authorRepository, credentialFacade);
        this.createAuthorUseCase = new CreateAuthorUseCase(
                authorRepository,
                authorIdGenerator,
                credentialFacade,
                roleFacade,
                roleAccessServiceFacade);
        this.deleteAuthorUseCase = new DeleteAuthorUseCase(authorRepository);
        this.findAuthorByCredentialIdUseCase = new FindAuthorByCredentialIdUseCase(authorRepository);
        this.findAuthorByIdUseCase = new FindAuthorByIdUseCase(authorRepository);
        this.findAuthorWithFiltersUseCase = new FindAuthorWithFiltersUseCase(authorRepository);
        this.updateAuthorUseCase = new UpdateAuthorUseCase(
                authorRepository,
                authorIdGenerator,
                credentialFacade,
                roleFacade,
                roleAccessServiceFacade);

    }
}
