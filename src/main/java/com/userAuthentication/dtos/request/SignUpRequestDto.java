package com.userAuthentication.dtos.request;

import com.userAuthentication.entities.Role;
import lombok.Builder;

@Builder
public record SignUpRequestDto(
        String firstName,
        String lastName,
        String email,
        String password,
        Role role
) {}
