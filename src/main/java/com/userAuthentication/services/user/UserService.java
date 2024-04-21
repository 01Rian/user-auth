package com.userAuthentication.services.user;

import com.userAuthentication.dtos.request.SignUpRequestDto;
import com.userAuthentication.dtos.response.UserDtoResponse;

public interface UserService {

    UserDtoResponse getAuthenticatedUser();

    UserDtoResponse updateUser(SignUpRequestDto dto);

    void delete();
}
