package com.campus.clean.arc.api.author.v1.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
