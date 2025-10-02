package com.zyx.vps.infra.http.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zyx.vps.application.dto.auth.ApiTokenDTO;
import com.zyx.vps.application.dto.auth.AuthCallbackDTO;
import com.zyx.vps.application.dto.auth.AuthRedirectDTO;
import com.zyx.vps.application.dto.auth.OAuthProviderDTO;
import com.zyx.vps.application.dto.entities.UserDTO;
import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.enums.OAuthProvider;
import com.zyx.vps.application.services.AuthService;
import com.zyx.vps.infra.database.jpa.mappers.UserMapper;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedRoute;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedUser;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/authentication")
public class AuthController {

  @Autowired
  private AuthService authService;

  @GetMapping("/me")
  @AuthenticatedRoute
  public ResponseEntity<UserDTO> getAuthenticatedUser(@AuthenticatedUser User user) {
    return new ResponseEntity<UserDTO>(UserMapper.toDTO(user), HttpStatusCode.valueOf(200));
  }

  @GetMapping("/oauth2/providers")
  public List<OAuthProviderDTO> getOAuth2Providers() {
    List<OAuthProvider> providers = Arrays.asList(OAuthProvider.values());
    List<OAuthProviderDTO> providersDto = new ArrayList<OAuthProviderDTO>();

    for (OAuthProvider provider : providers) {
      providersDto
          .add(new OAuthProviderDTO(provider.toString(), StringUtils.capitalize(provider.name().toLowerCase())));
    }

    return providersDto;
  }

  @GetMapping("/oauth2/{providerParam}/redirect")
  public ResponseEntity<AuthRedirectDTO> redirect(@PathVariable String providerParam) {
    OAuthProvider provider = parseProvider(providerParam);

    if (provider == null) {
      throw new ResponseStatusException(
          HttpStatusCode.valueOf(204), "Provider not found", null);
    }

    String redirectUrl = this.authService.redirect(provider);

    return new ResponseEntity<AuthRedirectDTO>(new AuthRedirectDTO(redirectUrl), HttpStatus.OK);
  }

  @PostMapping("oauth2/{providerParam}/callback")
  public void callback(@RequestBody AuthCallbackDTO dto,
      @PathVariable String providerParam, HttpServletResponse response)
      throws ClientProtocolException, IOException, Error {
    OAuthProvider provider = parseProvider(providerParam);

    if (provider == null) {
      throw new ResponseStatusException(
          HttpStatusCode.valueOf(404), "Provider not found", null);
    }

    ApiTokenDTO apiToken = this.authService.callback(OAuthProvider.GOOGLE, dto.getCode());

    Cookie cookie = new Cookie("authentication", apiToken.getToken());

    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setDomain("localhost");

    response.addCookie(cookie);
    response.setStatus(200);

    return;
  }

  private OAuthProvider parseProvider(String provider) {
    try {
      return OAuthProvider.valueOf(provider.toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

}
