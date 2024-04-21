package com.userAuthentication.services.auth.impl;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.request.SignInRequestDto;
import com.userAuthentication.dtos.response.JwtAuthenticationResponse;
import com.userAuthentication.entities.Role;
import com.userAuthentication.entities.User;
import com.userAuthentication.repository.UserRepository;
import com.userAuthentication.services.auth.AuthenticationService;
import com.userAuthentication.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequestDto request) {
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role() == null ? Role.USER : request.role())
                .build();

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public JwtAuthenticationResponse signIn(SignInRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}