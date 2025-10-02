package com.zyx.vps.application.entities;

import java.sql.Timestamp;
import java.util.List;

import com.zyx.vps.application.enums.ProjectStoreStatus;

public interface ProjectStorage {

  public String getId();

  public Project getProject();

  public String getRegion();

  public String getBucketName();

  public ProjectStoreStatus getStatus();

  public void setStatus(ProjectStoreStatus status);

  public ProjectStorageCredentials getCredentials();

  public Timestamp getUpdatedAt();

  public Timestamp getCreatedAt();

  public void setId(String id);

  public void setProject(Project project);

  public void setRegion(String region);

  public void setBucketName(String bucketName);

  public void setCredentials(ProjectStorageCredentials credentials);

  public void setUpdatedAt(Timestamp updatedAt);

  public void setCreatedAt(Timestamp createdAt);

  public List<ProjectStorageActivity> getActivities();

  public void setActivities(List<ProjectStorageActivity> activities);

}
