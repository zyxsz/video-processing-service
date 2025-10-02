package com.zyx.vps.application.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.zyx.vps.application.enums.StorageProviders;
import com.zyx.vps.application.enums.UploadStatus;

public class Upload {

  private String id;

  private String key;
  private String extName;
  private String contentType;
  private Long size;

  private String presignedUrl;

  private Project project;

  private StorageProviders storageProvider;
  private UploadStatus status;

  private Date updatedAt;
  private Date createdAt;

  public Upload(String id, String presignedUrl, String key, String extName, String contentType, Long size,
      StorageProviders storageProvider,
      UploadStatus status,
      Project project) {
    this.id = id != null ? id : UUID.randomUUID().toString();

    this.presignedUrl = presignedUrl;
    this.key = key;
    this.extName = extName;
    this.contentType = contentType;
    this.size = size;
    this.storageProvider = storageProvider;
    this.status = status;
    this.project = project;

    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());
  }

  public String getId() {
    return id;
  }

  public String getPresignedUrl() {
    return presignedUrl;
  }

  public String getKey() {
    return key;
  }

  public String getExtName() {
    return extName;
  }

  public String getContentType() {
    return contentType;
  }

  public Long getSize() {
    return size;
  }

  public StorageProviders getStorageProvider() {
    return storageProvider;
  }

  public UploadStatus getStatus() {
    return status;
  }

  public Project getProject() {
    return project;
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

  public void setPresignedUrl(String presignedUrl) {
    this.presignedUrl = presignedUrl;
  }

  public void setExtName(String extName) {
    this.extName = extName;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public void setStorageProvider(StorageProviders storageProvider) {
    this.storageProvider = storageProvider;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setStatus(UploadStatus status) {
    this.status = status;
  }
}
