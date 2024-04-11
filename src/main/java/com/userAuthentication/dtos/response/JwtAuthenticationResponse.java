package com.userAuthentication.dtos.response;

import lombok.Builder;

@Builder
public record JwtAuthenticationResponse(String token) {}
