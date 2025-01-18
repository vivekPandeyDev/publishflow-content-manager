package com.publishflow.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class WebConfiguration {

	@Value("${auth.keycloak.config.realm}")
	private String realm;
	@Value("${auth.keycloak.config.domain}")
	private String baseUrl;

	@Value("${auth.keycloak.config.admin-client-id}")
	private String adminClientId;

	@Value("${auth.keycloak.config.admin-client-secret}")
	private String adminClientSecret;

	@Bean
	@Primary
	ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setFieldMatchingEnabled(true).setMatchingStrategy(MatchingStrategies.LOOSE);
		return mapper;
	}



	@Bean
	Keycloak keycloak(){
		return KeycloakBuilder
				.builder()
				.realm(realm)
				.serverUrl(baseUrl)
				.clientId(adminClientId)
				.clientSecret(adminClientSecret)
				.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
				.build();
	}


	@Bean
	RealmResource realmResource(Keycloak keycloak){
		return keycloak.realm(realm);
	}

	@Bean
	UsersResource usersResource(RealmResource realmResource){
		return realmResource.users();
	}



}
