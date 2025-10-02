package com.zyx.vps.application.dto.entities;

import java.util.Date;
import java.util.List;

import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.application.enums.ProjectStoreStatus;

public class ProjectStorageDTO {
  private String id;
  private String bucketName;
  private String region;
  // private List<ProjectStorageActivityDTO> activities;
  private ProjectStoreStatus status;
  private Date updatedAt;
  private Date createdAt;

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setStatus(ProjectStoreStatus status) {
    this.status = status;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getBucketName() {
    return bucketName;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public String getId() {
    return id;
  }

  public String getRegion() {
    return region;
  }

  public ProjectStoreStatus getStatus() {
    return status;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  // public List<ProjectStorageActivityDTO> getActivities() {
  // return activities;
  // }

  // public void setActivities(List<ProjectStorageActivityDTO> activities) {
  // this.activities = activities;
  // }
}
