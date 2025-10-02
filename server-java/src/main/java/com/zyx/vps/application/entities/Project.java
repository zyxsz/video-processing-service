package com.zyx.vps.application.entities;

import java.util.Date;

public interface Project {

  public String getId();

  public String getName();

  public User getUser();

  public Date getUpdatedAt();

  public Date getCreatedAt();

  public void setId(String id);

  public void setName(String name);

  public void setUser(User user);

  public void setUpdatedAt(Date updatedAt);

  public void setCreatedAt(Date createdAt);

}
