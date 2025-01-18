package com.publishflow.keycloak;

import com.publishflow.exception.ServiceException;
import com.publishflow.keycloak.dto.RegisterDto;
import com.publishflow.keycloak.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.internal.FinalizedClientResponse;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeyCloakServiceImpl implements KeyCloakService {

    private static final Logger log = LoggerFactory.getLogger(KeyCloakServiceImpl.class);
    private final RealmResource realmResource;
    private final UsersResource usersResource;
    private final ModelMapper modelMapper;

    @Value("${auth.keycloak.config.default-role}")
    private String defaultRoleName;


    public UserDto createUser(RegisterDto registerDto) {
        try (var response = usersResource.create(registerDto.toUserRepresentation())) {
            var isUserCreated = Objects.equals(201, response.getStatus());
            if (!isUserCreated) {
                var reason = ((FinalizedClientResponse) response).getReasonPhrase();
                throw new ServiceException("user cannot be created, reason -> : " + reason);
            } else {
                // Get user ID
                var users = usersResource.search(registerDto.getUsername());
                if (users.isEmpty()) {
                    throw new ServiceException("user cannot be find by given username: " + registerDto.getUsername());
                }
                return UserUtility.getUserDto(users.getFirst());
            }

        }
    }



    public void deleteUser(String id){
        try(var response = usersResource.delete(id)){
            log.info("user delete status: {}",response.getStatus());
        }
    }

    @Override
    public UserRepresentation getKeycloakUser(String username) {
        var users = usersResource.search(username);
        if (users.isEmpty()) {
            throw new ServiceException("user cannot be find by given username: " + username);
        }
        return users.getFirst();
    }


    @Override
    public RoleRepresentation getKeycloakDefaultRoles() {
        return realmResource.roles().get(defaultRoleName).toRepresentation();
    }

    @Override
    public boolean isUserExist(String username) {
        var users = usersResource.search(username);
        return !users.isEmpty();
    }


}
