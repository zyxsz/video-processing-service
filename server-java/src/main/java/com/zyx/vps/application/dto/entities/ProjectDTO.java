package com.zyx.vps.application.dto.entities;

import java.util.Date;

import com.zyx.vps.application.entities.User;

public class ProjectDTO {
  private String id;

  private String name;

  private Date updatedAt;
  private Date createdAt;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
}
