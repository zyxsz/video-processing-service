package com.zyx.vps.application.entities;

import java.util.Date;

public interface ApiToken {
  public String getId();

  public User getUser();

  public String getToken();

  public Boolean isEnabled();

  public Date getExpiresAt();

  public Date getUpdatedAt();

  public Date getCreatedAt();

  public Boolean isExpired();

  public void setCreatedAt(Date createdAt);

  public void setEnabled(Boolean enabled);

  public void setExpiresAt(Date expiresAt);

  public void setToken(String token);

  public void setUpdatedAt(Date updatedAt);

  public void setUser(User user);

}
