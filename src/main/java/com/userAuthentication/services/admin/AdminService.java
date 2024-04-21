package com.userAuthentication.services.admin;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.response.UserDtoResponse;

import java.util.Collection;

public interface AdminService {

    UserDtoResponse findByEmail(String email);

    Collection<UserDtoResponse> findAll();

    UserDtoResponse updateUser(SignUpRequestDto dto, Long id);

    void deleteById(Long id);
}
