package com.userAuthentication.services.admin.impl;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.response.UserDtoResponse;
import com.userAuthentication.entities.User;
import com.userAuthentication.repository.UserRepository;
import com.userAuthentication.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDtoResponse findByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();

        return UserDtoResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserDtoResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserDtoResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .build()
                ).toList();
    }

    @Override
    @Transactional
    public UserDtoResponse updateUser(SignUpRequestDto dto, Long id) {
        var user = userRepository.findById(id);

        if (user.isEmpty()) return null;

        setFields(dto, user.get());

        User updatedUser = userRepository.save(user.get());

        return UserDtoResponse.builder()
                .id(updatedUser.getId())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .email(updatedUser.getEmail())
                .build();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private void setFields(SignUpRequestDto dto, User user) {
        user.setFirstName(Objects.requireNonNullElse(dto.firstName(), user.getFirstName()));
        user.setLastName(Objects.requireNonNullElse(dto.lastName(), user.getLastName()));
        user.setEmail(Objects.requireNonNullElse(dto.email(), user.getEmail()));
        user.setPassword(Objects.requireNonNullElse(passwordEncoder.encode(dto.password()), user.getPassword()));
        user.setRole(Objects.requireNonNullElse(dto.role(), user.getRole()));
    }
}
