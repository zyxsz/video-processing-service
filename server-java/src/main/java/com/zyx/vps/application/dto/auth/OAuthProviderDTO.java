package com.zyx.vps.application.dto.auth;

public class OAuthProviderDTO {
  private String name;
  private String id;

  public OAuthProviderDTO(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
