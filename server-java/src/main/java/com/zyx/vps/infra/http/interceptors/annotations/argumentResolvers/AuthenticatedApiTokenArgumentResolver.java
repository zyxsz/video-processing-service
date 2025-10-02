package com.zyx.vps.infra.http.interceptors.annotations.argumentResolvers;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.zyx.vps.application.entities.ApiToken;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedApiToken;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticatedApiTokenArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(ApiToken.class) &&
        parameter.hasParameterAnnotation(AuthenticatedApiToken.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    ApiToken authenticatedApiToken = (ApiToken) request.getAttribute("apiToken");
    return authenticatedApiToken;
  }
}
