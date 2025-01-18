package com.publishflow.token;

import com.publishflow.token.dto.LoginRequest;
import com.publishflow.token.dto.Token;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public Token getToken(@Valid @RequestBody LoginRequest loginRequest) {
        return tokenService.getToken(loginRequest.getUsername(), loginRequest.getPassword());
    }


}
