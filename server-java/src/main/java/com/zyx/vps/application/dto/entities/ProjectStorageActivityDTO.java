package com.zyx.vps.application.dto.entities;

import java.util.Date;

import com.zyx.vps.application.enums.ProjectStorageActivityOperationType;

public class ProjectStorageActivityDTO {

  private String id;
  private String operationData;
  private ProjectStorageActivityOperationType operationType;
  private Date executedAt;
  private Date updatedAt;
  private Date createdAt;

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

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getExecutedAt() {
    return executedAt;
  }

  public String getId() {
    return id;
  }

  public String getOperationData() {
    return operationData;
  }

  public ProjectStorageActivityOperationType getOperationType() {
    return operationType;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

}
