package com.userAuthentication.services;

import com.userAuthentication.dtos.response.UserDtoResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    UserDtoResponse findByEmail(String email);
}
