package com.zyx.vps.application.dto.auth;

import com.zyx.vps.application.entities.ApiToken;
import com.zyx.vps.application.entities.User;

public class AuthVerifyTokenDTO {
  private ApiToken apiToken;
  private User user;

  public AuthVerifyTokenDTO(ApiToken apiToken, User user) {
    this.apiToken = apiToken;
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public ApiToken getApiToken() {
    return apiToken;
  }

  public void setApiToken(ApiToken apiToken) {
    this.apiToken = apiToken;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
