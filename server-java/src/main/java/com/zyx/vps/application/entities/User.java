package com.zyx.vps.application.entities;

import java.util.Date;

import com.zyx.vps.application.enums.OAuthProvider;

public interface User {

  public void setId(String id);

  public String getId();

  public String getUsername();

  public String getEmail();

  public String getAvatar();

  public Boolean isEmailVerifiedByProvider();

  public OAuthProvider getProvider();

  public Date getUpdatedAt();

  public Date getCreatedAt();

  public void setProvider(OAuthProvider provider);

  public void setAvatar(String avatar);

  public void setCreatedAt(Date createdAt);

  public void setEmail(String email);

  public void setIsEmailVerifiedByProvider(Boolean isEmailVerifiedByProvider);

  public void setUpdatedAt(Date updatedAt);

  public void setUsername(String username);

}
