package com.publishflow.user;

import com.publishflow.keycloak.dto.RegisterDto;
import com.publishflow.keycloak.dto.UserDto;

public interface UserService {
    UserDto saveUser(RegisterDto registerDto);
    UserDto getUserByUsername(String username);
    void deleteUserById(String id);
    boolean isUserExist(String username);
}
