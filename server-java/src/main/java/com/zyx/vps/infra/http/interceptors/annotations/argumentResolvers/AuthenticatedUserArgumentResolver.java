package com.zyx.vps.infra.http.interceptors.annotations.argumentResolvers;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.zyx.vps.application.entities.User;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedUser;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(User.class) &&
        parameter.hasParameterAnnotation(AuthenticatedUser.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    // Retrieve the custom value from wherever it was set in your interceptor
    User authenticatedUser = (User) request.getAttribute("user");
    return authenticatedUser;
  }
}
