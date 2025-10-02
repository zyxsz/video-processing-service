package com.zyx.vps.application.entities.local;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.User;

public class ProjectLocal implements Project {
  private String id;

  private String name;
  private User user;

  private Date updatedAt;
  private Date createdAt;

  public ProjectLocal(String id, String name, User user) {
    if (id != null) {
      this.id = id;
    } else {
      this.id = UUID.randomUUID().toString();
    }

    this.name = name;
    this.user = user;
    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public User getUser() {
    return user;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setId(String id) {
    this.id = id;
  }
}
