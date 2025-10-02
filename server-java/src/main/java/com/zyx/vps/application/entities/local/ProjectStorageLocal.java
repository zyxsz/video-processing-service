package com.zyx.vps.application.entities.local;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.application.entities.ProjectStorageCredentials;
import com.zyx.vps.application.enums.ProjectStoreStatus;

public class ProjectStorageLocal implements ProjectStorage {
  private String id;

  private Project project;

  private String region;
  private String bucketName;

  private ProjectStoreStatus status;

  private ProjectStorageCredentials credentials;

  private List<ProjectStorageActivity> activities;

  private Timestamp updatedAt;
  private Timestamp createdAt;

  public ProjectStorageLocal(String id) {
    this.id = id != null ? id : UUID.randomUUID().toString();

    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());
  }

  public String getId() {
    return id;
  }

  public Project getProject() {
    return project;
  }

  public String getRegion() {
    return region;
  }

  public String getBucketName() {
    return bucketName;
  }

  public ProjectStoreStatus getStatus() {
    return status;
  }

  public ProjectStorageCredentials getCredentials() {
    return credentials;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setStatus(ProjectStoreStatus status) {
    this.status = status;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public void setCredentials(ProjectStorageCredentials credentials) {
    this.credentials = credentials;
  }

  public List<ProjectStorageActivity> getActivities() {
    return activities;
  }

  public void setActivities(List<ProjectStorageActivity> activities) {
    this.activities = activities;
  }

}
