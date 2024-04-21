package com.userAuthentication.services.user.impl;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.response.UserDtoResponse;
import com.userAuthentication.entities.User;
import com.userAuthentication.repository.UserRepository;
import com.userAuthentication.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDtoResponse getAuthenticatedUser() {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) userAuth.getPrincipal();

        return UserDtoResponse.builder()
                .id(currentUser.getId())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .email(currentUser.getEmail())
                .build();
    }

    @Override
    @Transactional
    public UserDtoResponse updateUser(SignUpRequestDto dto) {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) userAuth.getPrincipal();

        setFields(dto, currentUser);

        User updatedUser = userRepository.save(currentUser);

        return UserDtoResponse.builder()
                .id(updatedUser.getId())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .email(updatedUser.getEmail())
                .build();
    }

    private void setFields(SignUpRequestDto dto, User currentUser) {
        currentUser.setFirstName(Objects.requireNonNullElse(dto.firstName(), currentUser.getFirstName()));
        currentUser.setLastName(Objects.requireNonNullElse(dto.lastName(), currentUser.getLastName()));
        currentUser.setEmail(Objects.requireNonNullElse(dto.email(), currentUser.getEmail()));
        currentUser.setPassword(Objects.requireNonNullElse(passwordEncoder.encode(dto.password()), currentUser.getPassword()));
        currentUser.setRole(Objects.requireNonNullElse(dto.role(), currentUser.getRole()));
    }

    @Override
    @Transactional
    public void delete() {
        Authentication userAuth = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) userAuth.getPrincipal();

        userRepository.delete(currentUser);
    }
}
