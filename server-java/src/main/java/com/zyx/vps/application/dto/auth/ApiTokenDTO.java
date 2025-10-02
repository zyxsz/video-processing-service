package com.zyx.vps.application.dto.auth;

import java.sql.Timestamp;
import java.util.Date;

public class ApiTokenDTO {

  private String token;
  private Timestamp expiresAt;

  public ApiTokenDTO(String token, Timestamp expiresAt) {
    this.token = token;
    this.expiresAt = expiresAt;
  }

  public String getToken() {
    return token;
  }

  public Timestamp getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Timestamp expiresAt) {
    this.expiresAt = expiresAt;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
