package com.publishflow.user;


import com.publishflow.exception.ServiceException;
import com.publishflow.keycloak.KeyCloakService;
import com.publishflow.keycloak.UserUtility;
import com.publishflow.keycloak.dto.RegisterDto;
import com.publishflow.keycloak.dto.UserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final KeyCloakService keyCloakService;

    private static final String USER_DETAIL = "user details -> {}";


    @Override
    public UserDto saveUser(RegisterDto registerDto) {
        if (isUserExist(registerDto.getUsername())) {
            log.error("Cannot save user as username is already present in database {}", registerDto.getEmail());
            throw new ServiceException("User is already present choose different username!", HttpStatus.FOUND, "User Already exists");
        }
        return keyCloakService.createUser(registerDto);
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public UserDto getUserByUsername(String username) {
        final var savedUser = keyCloakService.getKeycloakUser(username);
        if (log.isDebugEnabled()) log.debug(USER_DETAIL, savedUser);
        return UserUtility.getUserDto(savedUser);
    }

    @Override
    public void deleteUserById(String id) {
        keyCloakService.deleteUser(id);
    }

    @Override
    public boolean isUserExist(String username) {
        return keyCloakService.isUserExist(username);
    }


}
