package com.userAuthentication.services;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.response.UserDtoResponse;

import java.util.Collection;

public interface UserService {

    UserDtoResponse findByEmail(String email);

    UserDtoResponse authenticatedUser();

    Collection<UserDtoResponse> findAll();

    UserDtoResponse updateUser(SignUpRequestDto dto, Long id);

    void deleteById(Long id);
}
