package com.zyx.vps.infra.http.interceptors;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zyx.vps.application.dto.auth.AuthVerifyTokenDTO;
import com.zyx.vps.application.errors.UnauthorizedException;
import com.zyx.vps.application.services.AuthService;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedRoute;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
  private AuthService authService;

  public AuthenticationInterceptor(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod) {
      try {

        final AuthenticatedRoute authenticatedAnnotation = ((HandlerMethod) handler).getMethod()
            .getAnnotation((AuthenticatedRoute.class));

        if (authenticatedAnnotation == null) {
          return true;
        }

        if (request.getCookies() == null)
          throw new UnauthorizedException("Missing authorization cookie (null)");

        if (request.getCookies().length <= 0)
          throw new UnauthorizedException("Missing authorization cookie (empty)");

        Cookie cookie = Arrays.stream(request.getCookies())
            .filter(c -> c.getName().equals("authentication")).findAny().orElse(null);

        if (cookie == null)
          throw new UnauthorizedException("Missing authorization cookie");

        // String tokenType = authorizationHeader.split(" ")[0];
        // String accessToken = authorizationHeader.split(" ")[1];

        // if (tokenType == null || !tokenType.equals("Bearer"))
        // throw new UnauthorizedException("Unauthorized");

        AuthVerifyTokenDTO authVerifyTokenDTO = this.authService.verifyToken(cookie.getValue());

        if (authVerifyTokenDTO == null)
          throw new UnauthorizedException("Unauthorized");

        request.setAttribute("user", authVerifyTokenDTO.getUser());
        request.setAttribute("apiToken", authVerifyTokenDTO.getApiToken());

        return true;
      } catch (ClassCastException e) {
        return true;
      }

    }

    return true;

  }
}
