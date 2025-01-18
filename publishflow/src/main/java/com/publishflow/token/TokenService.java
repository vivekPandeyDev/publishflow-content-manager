package com.publishflow.token;

import com.publishflow.exception.RestClientException;
import com.publishflow.token.dto.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    @Value("${auth.keycloak.config.admin-client-id}")
    private String clientId;

    @Value("${auth.keycloak.config.admin-client-secret}")
    private String clientSecret;

    @Value("${jwt.token-uri}")
    private String tokenUri;

    private final RestClient.Builder restBuilder;

    public Token getToken(String username, String password){
        MultiValueMap<String,String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-Type",MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        String requestBody = String.format("grant_type=password&username=%s&password=%s&client_id=%s&client_secret=%s",
                username, password, clientId,clientSecret);


        var response = restBuilder.build().post()
                .uri(tokenUri)
                .headers( httpHeaders -> httpHeaders.addAll(headerMap))
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,this::handleError)
                .toEntity(Token.class);

       return response.getBody();
    }

    private void handleError(HttpRequest req, ClientHttpResponse resp) throws IOException {
        String result = new BufferedReader(new InputStreamReader(resp.getBody()))
                .lines().parallel().collect(Collectors.joining("\n"));
        throw new RestClientException(result);
    }

}
