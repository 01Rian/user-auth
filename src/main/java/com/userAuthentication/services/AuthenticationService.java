package com.userAuthentication.services;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.request.SignInRequestDto;
import com.userAuthentication.dtos.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequestDto request);

    JwtAuthenticationResponse signIn(SignInRequestDto request);
}
