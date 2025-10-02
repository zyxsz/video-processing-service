package com.zyx.vps.infra.http.configs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zyx.vps.application.services.AuthService;
import com.zyx.vps.infra.configs.ApplicationConfig;
import com.zyx.vps.infra.http.interceptors.AuthenticationInterceptor;
import com.zyx.vps.infra.http.interceptors.annotations.argumentResolvers.AuthenticatedApiTokenArgumentResolver;
import com.zyx.vps.infra.http.interceptors.annotations.argumentResolvers.AuthenticatedUserArgumentResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

  private AuthService authService;

  public WebMvcConfig(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AuthenticationInterceptor(this.authService));
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new AuthenticatedUserArgumentResolver());
    resolvers.add(new AuthenticatedApiTokenArgumentResolver());
  }

  @Bean
  public ApplicationConfig applicationConfig() throws IOException {
    ApplicationConfig applicationConfig = new ApplicationConfig().initialize();

    return applicationConfig;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*").allowCredentials(true);
  }

  // @Bean
  // public WebMvcConfigurer corsConfigurer() {
  // return new WebMvcConfigurer() {
  // @Override
  // public void addCorsMappings(CorsRegistry registry) {
  // registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST",
  // "PUT", "DELETE", "OPTIONS");
  // }
  // };
  // }

  // @Override
  // public void addCorsMappings(CorsRegistry registry) {
  // registry.addMapping("/**");
  // registry.allowedOrigins("*");
  // }
}