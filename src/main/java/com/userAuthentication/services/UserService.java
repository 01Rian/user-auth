package com.userAuthentication.services;

import com.userAuthentication.dtos.response.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    UserDetailsService userDetailsService();

    @Transactional(readOnly = true)
    UserDto findByEmail(String email);
}
