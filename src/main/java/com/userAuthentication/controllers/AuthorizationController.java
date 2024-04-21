package com.userAuthentication.controllers;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.response.UserDtoResponse;
import com.userAuthentication.services.admin.AdminService;
import com.userAuthentication.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/resource")
public class AuthorizationController {

    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("/me")
    public ResponseEntity<UserDtoResponse> authenticatedUser() {
        var user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDtoResponse> updatedUserAuth(@RequestBody SignUpRequestDto dto) {
        var user = userService.updateUser(dto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> delete() {
        userService.delete();
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<Collection<UserDtoResponse>> findAll() {
        Collection<UserDtoResponse> users = adminService.findAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/email")
    public ResponseEntity<UserDtoResponse> findByEmail(@RequestBody UserDtoResponse userDtoResponse) {
        var user = adminService.findByEmail(userDtoResponse.email());
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updatedUserAuth(@RequestBody SignUpRequestDto dto, @PathVariable Long id) {
        UserDtoResponse updatedUser = adminService.updateUser(dto, id);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        adminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
