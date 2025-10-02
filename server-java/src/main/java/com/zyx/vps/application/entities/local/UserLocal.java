package com.zyx.vps.application.entities.local;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.enums.OAuthProvider;

public class UserLocal implements User {

  private String id;
  private String username;
  private String email;
  private Boolean isEmailVerifiedByProvider;
  private String avatar;
  private OAuthProvider provider;
  private Date updatedAt;
  private Date createdAt;

  public UserLocal(String id, String username, String email, Boolean isEmailVerifiedByProvider, String avatar,
      OAuthProvider provider) {
    if (id != null) {
      this.id = id;
    } else {
      this.id = UUID.randomUUID().toString();
    }

    this.username = username;
    this.email = email;
    this.avatar = avatar;
    this.provider = provider;
    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());

    if (isEmailVerifiedByProvider == null) {
      this.isEmailVerifiedByProvider = Boolean.FALSE;
    } else {
      this.isEmailVerifiedByProvider = isEmailVerifiedByProvider;
    }
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

  public Boolean isEmailVerifiedByProvider() {
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
