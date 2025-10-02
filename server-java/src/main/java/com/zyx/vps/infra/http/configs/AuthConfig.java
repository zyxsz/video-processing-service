package com.zyx.vps.infra.http.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyx.vps.application.repositories.ApiTokenRepository;
import com.zyx.vps.application.repositories.UsersRepository;
import com.zyx.vps.application.services.AuthService;
import com.zyx.vps.application.services.GoogleOAuthService;

@Configuration
public class AuthConfig {

  @Bean
  public GoogleOAuthService googleOauthService() {
    GoogleOAuthService googleOAuthService = new GoogleOAuthService();

    return googleOAuthService;
  }

}
