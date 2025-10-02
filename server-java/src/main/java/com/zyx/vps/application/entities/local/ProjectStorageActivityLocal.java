package com.zyx.vps.application.entities.local;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.application.enums.ProjectStorageActivityOperationType;

public class ProjectStorageActivityLocal implements ProjectStorageActivity {

  private String id;
  private ProjectStorage storage;

  private String operationData;
  private ProjectStorageActivityOperationType operationType;

  private Date executedAt;

  private Date updatedAt;
  private Date createdAt;

  public ProjectStorageActivityLocal(String id) {
    this.id = id != null ? id : UUID.randomUUID().toString();

    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());
  }

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
    this.storage = storage;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

}
