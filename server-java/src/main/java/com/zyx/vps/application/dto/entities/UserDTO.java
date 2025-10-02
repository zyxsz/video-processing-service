package com.zyx.vps.application.dto.entities;

import java.util.Date;

import com.zyx.vps.application.enums.OAuthProvider;

public class UserDTO {

  private String id;
  private String username;
  private String email;
  private Boolean isEmailVerifiedByProvider;
  private String avatar;
  private OAuthProvider provider;
  private Date updatedAt;
  private Date createdAt;

  public UserDTO() {

  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getEmail() {
    return this.email;
  }

  public String getAvatar() {
    return avatar;
  }

  public Boolean getIsEmailVerifiedByProvider() {
    return isEmailVerifiedByProvider;
  }

  public OAuthProvider getProvider() {
    return provider;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setProvider(OAuthProvider provider) {
    this.provider = provider;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setIsEmailVerifiedByProvider(Boolean isEmailVerifiedByProvider) {
    this.isEmailVerifiedByProvider = isEmailVerifiedByProvider;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
