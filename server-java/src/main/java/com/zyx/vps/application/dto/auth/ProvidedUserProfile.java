package com.zyx.vps.application.dto.auth;

import java.util.Optional;

public class ProvidedUserProfile {
  private String id;
  private String email;
  private Boolean isVerified;
  private String username;
  private String originalName;
  private String avatar;

  public ProvidedUserProfile(String id, String email, Boolean isVerified, String username,
      String originalName, String avatar) {
    this.id = id;
    this.email = email;
    this.isVerified = isVerified;
    this.username = username;
    this.originalName = originalName;
    this.avatar = avatar;
  }

  public String getAvatar() {
    return avatar;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getId() {
    return id;
  }

  public Boolean getIsVerified() {
    return isVerified;
  }

  public String getOriginalName() {
    return originalName;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setIsVerified(Boolean isVerified) {
    this.isVerified = isVerified;
  }

  public void setOriginalName(String originalName) {
    this.originalName = originalName;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
