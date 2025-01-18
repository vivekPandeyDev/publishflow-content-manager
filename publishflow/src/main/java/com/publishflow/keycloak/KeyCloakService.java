package com.publishflow.keycloak;

import com.publishflow.keycloak.dto.RegisterDto;
import com.publishflow.keycloak.dto.UserDto;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;


public interface KeyCloakService {
    UserDto createUser(RegisterDto registerDto);
    void deleteUser(String id);
    UserRepresentation getKeycloakUser(String username);
    RoleRepresentation getKeycloakDefaultRoles();
    boolean isUserExist(String username);
}
