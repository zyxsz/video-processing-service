package com.zyx.vps.infra.database.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "project")
@Table(name = "projects")
public class JPAProject implements Serializable, Project {

  @Id
  private String id;

  private String name;

  @ManyToOne()
  @JoinColumn(name = "userId", referencedColumnName = "id")
  private JPAUser user;

  @LastModifiedDate
  private Date updatedAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Date createdAt;

  @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
  private List<ProjectStorageJPA> storages;

  public JPAProject() {
  }

  public JPAProject(String id) {
    this.id = id != null ? id : UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public JPAUser getUser() {
    return user;
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

  public void setUser(User user) {
    this.user = (JPAUser) user;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}
