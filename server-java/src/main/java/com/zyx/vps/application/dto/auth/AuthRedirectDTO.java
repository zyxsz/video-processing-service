package com.zyx.vps.application.dto.auth;

public class AuthRedirectDTO {
  private String redirectUrl;

  public AuthRedirectDTO(String redirectUrl) {

    this.redirectUrl = redirectUrl;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }
}
