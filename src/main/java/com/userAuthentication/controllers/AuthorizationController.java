package com.userAuthentication.controllers;

import com.userAuthentication.dtos.response.UserDtoResponse;
import com.userAuthentication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/resource")
public class AuthorizationController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDtoResponse> findByEmail(@RequestBody UserDtoResponse userDtoResponse) {
        return ResponseEntity.ok(userService.findByEmail(userDtoResponse.email()));
    }
}
