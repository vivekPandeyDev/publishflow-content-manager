package com.publishflow.keycloak;

import com.publishflow.keycloak.dto.UserDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class UserUtility {

    private static  ModelMapper modelMapperStatic;
    private final ModelMapper modelMapper;


    public static UserDto getUserDto(UserRepresentation user) {
        UserDto userDto = modelMapperStatic.map(user, UserDto.class);
        userDto.setUserId(UUID.fromString(user.getId()));
        return userDto;
    }

    @PostConstruct
    public void postConstruct(){
        modelMapperStatic = modelMapper;
    }
}
