package com.userAuthentication.dtos.request;

import lombok.Builder;

@Builder
public record SignInRequestDto(String email, String password) {}
