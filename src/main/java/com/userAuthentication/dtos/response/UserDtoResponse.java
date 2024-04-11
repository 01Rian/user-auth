package com.userAuthentication.dtos.response;

import lombok.Builder;

@Builder
public record UserDtoResponse(
        Long    id,
        String  firstName,
        String  lastName,
        String  email
) {}
