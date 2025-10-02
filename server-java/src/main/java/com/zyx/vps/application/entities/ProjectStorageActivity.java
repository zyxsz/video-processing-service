package com.zyx.vps.application.entities;

import java.util.Date;

import com.zyx.vps.application.enums.ProjectStorageActivityOperationType;

public interface ProjectStorageActivity {
  public String getId();

  public ProjectStorage getStorage();

  public String getOperationData();

  public ProjectStorageActivityOperationType getOperationType();

  public Date getExecutedAt();

  public Date getUpdatedAt();

  public Date getCreatedAt();

  public void setCreatedAt(Date createdAt);

  public void setExecutedAt(Date executedAt);

  public void setId(String id);

  public void setOperationData(String operationData);

  public void setOperationType(ProjectStorageActivityOperationType operationType);

  public void setStorage(ProjectStorage storage);

  public void setUpdatedAt(Date updatedAt);
}
