package com.zyx.vps.application.entities.local;

import com.zyx.vps.application.entities.ApiToken;
import com.zyx.vps.application.entities.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class ApiTokenLocal implements ApiToken {

  private String _id;

  private User user;

  private String token;
  private Boolean enabled;

  private Date expiresAt;

  private Date updatedAt;
  private Date createdAt;

  public ApiTokenLocal(String _id, String userId, String token, Date expiresAt) {
    this.token = token;
    this.expiresAt = expiresAt;
    this.enabled = true;
    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());

    if (_id != null) {
      this._id = _id;
    } else {
      this._id = UUID.randomUUID().toString();
    }
  }

  public String getId() {
    return this._id;
  }

  public User getUser() {
    return this.user;
  }

  public String getToken() {
    return this.token;
  }

  public Boolean isEnabled() {
    return this.enabled;
  }

  public Date getExpiresAt() {
    return this.expiresAt;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public Boolean isExpired() {
    return this.expiresAt.before(Timestamp.from(Instant.now()));
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
