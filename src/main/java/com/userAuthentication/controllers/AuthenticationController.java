package com.userAuthentication.controllers;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.request.SignInRequestDto;
import com.userAuthentication.dtos.response.JwtAuthenticationResponse;
import com.userAuthentication.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody SignUpRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.signUp(requestDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.signIn(requestDto));
    }
}
