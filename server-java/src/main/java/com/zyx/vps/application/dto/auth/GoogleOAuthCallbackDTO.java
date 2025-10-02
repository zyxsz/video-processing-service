package com.zyx.vps.application.dto.auth;

public class GoogleOAuthCallbackDTO {
  private String idToken;
  private String accessToken;
  private String scope;
  private String tokenType;
  private Integer expiresIn;

  public GoogleOAuthCallbackDTO(String idToken, String accessToken, String tokenType, String scope, Integer expiresIn) {
    this.accessToken = accessToken;
    this.idToken = idToken;
    this.scope = scope;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public Integer getExpiresIn() {
    return expiresIn;
  }

  public String getIdToken() {
    return idToken;
  }

  public String getScope() {
    return scope;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setExpiresIn(Integer expiresIn) {
    this.expiresIn = expiresIn;
  }

  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

}
