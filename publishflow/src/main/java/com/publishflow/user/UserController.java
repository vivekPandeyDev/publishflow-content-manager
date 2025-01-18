package com.publishflow.user;

import com.publishflow.exception.ServiceException;
import com.publishflow.file.FileService;
import com.publishflow.file.ImageService;
import com.publishflow.keycloak.dto.RegisterDto;
import com.publishflow.keycloak.dto.UserDto;
import com.publishflow.response.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unused")
@SecurityRequirement( name = "keycloak")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    private final FileService fileService;

    @Value("${file.upload.location}")
    private String uploadLocation;

    @Value("${base.api.url}")
    private String baseUrl;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDto> register(
            @Valid @RequestPart("user") RegisterDto registerDto,
            @RequestParam(name = "image", required = false) MultipartFile image) throws IOException {
        if(Objects.isNull(image)){
            throw new ServiceException("User profile image not available", HttpStatus.BAD_REQUEST, "Image Not Found");
        }
        if (userService.isUserExist(registerDto.getUsername())) {
            throw new ServiceException("User is already present choose different username!", HttpStatus.FOUND, "User Already exists");
        }
        String savedImageName = imageService.save(uploadLocation, image, registerDto.getUsername());
        registerDto.setProfileUrl(getProfileUrl(savedImageName));
        final var userDto = userService.saveUser(registerDto);

        return new ApiResponse<>(true, Map.of("user", userDto), "User Registered Successfully!!!!");
    }

    @GetMapping("name/{uniqueName}")
    public ApiResponse<UserDto> getUserDetailByUniqueName(@PathVariable("uniqueName") String uniqueName) {
        var savedUserDto = userService.getUserByUsername(uniqueName);
        return new ApiResponse<>(true, Map.of("user", savedUserDto), "User Detail Fetched With UniqueName Successfully!!!!");
    }

    @GetMapping(value = "/images/{uniqueName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserImage(@PathVariable("uniqueName") String uniqueName) throws IOException {
        log.info("unique name: {}", uniqueName);
        return fileService.getResource(fileService.getFolderPath(uploadLocation), uniqueName);
    }

    private String getProfileUrl(String imageName) {
        return String.format("%s/users/images/%s", baseUrl, imageName);
    }


}