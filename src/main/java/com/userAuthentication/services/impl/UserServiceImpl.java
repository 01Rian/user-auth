package com.userAuthentication.services.impl;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.response.UserDtoResponse;
import com.userAuthentication.entities.User;
import com.userAuthentication.repository.UserRepository;
import com.userAuthentication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

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
    public UserDtoResponse authenticatedUser() {
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
    @Transactional(readOnly = true)
    public Collection<UserDtoResponse> findAll() {
        Collection<UserDtoResponse> users = userRepository.findAll()
                .stream()
                .map(user -> UserDtoResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .build()
                ).toList();

        return users;
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
