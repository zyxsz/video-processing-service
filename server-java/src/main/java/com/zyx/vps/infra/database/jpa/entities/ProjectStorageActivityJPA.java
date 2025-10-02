package com.zyx.vps.infra.database.jpa.entities;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.application.enums.ProjectStorageActivityOperationType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "projectStorageActivity")
@Table(name = "projectStorageActivities")
public class ProjectStorageActivityJPA implements ProjectStorageActivity {
  @Id
  private String id;

  @ManyToOne()
  @JoinColumn(name = "storageId")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ProjectStorageJPA storage;

  private String operationData;

  @Enumerated(EnumType.STRING)
  private ProjectStorageActivityOperationType operationType;

  private Date executedAt;

  @LastModifiedDate
  private Date updatedAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Date createdAt;

  public String getId() {
    return id;
  }

  public ProjectStorage getStorage() {
    return storage;
  }

  public String getOperationData() {
    return operationData;
  }

  public ProjectStorageActivityOperationType getOperationType() {
    return operationType;
  }

  public Date getExecutedAt() {
    return executedAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setExecutedAt(Date executedAt) {
    this.executedAt = executedAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setOperationData(String operationData) {
    this.operationData = operationData;
  }

  public void setOperationType(ProjectStorageActivityOperationType operationType) {
    this.operationType = operationType;
  }

  public void setStorage(ProjectStorage storage) {
    this.storage = (ProjectStorageJPA) storage;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
