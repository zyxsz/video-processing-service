package com.zyx.vps.application.dto.projects;

import com.zyx.vps.application.enums.StorageProviders;

import jakarta.validation.constraints.NotNull;

public class CreateProjectStorageDTO {

  @NotNull
  private StorageProviders provider;

  @NotNull
  private String region;

  @NotNull
  private String bucketName;

  @NotNull
  private String accessKeyId;

  @NotNull
  private String secretAccessKey;

  public StorageProviders getProvider() {
    return provider;
  }

  public String getRegion() {
    return region;
  }

  public String getBucketName() {
    return bucketName;
  }

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public String getSecretAccessKey() {
    return secretAccessKey;
  }

  public void setProvider(StorageProviders provider) {
    this.provider = provider;
  }

  public void setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setSecretAccessKey(String secretAccessKey) {
    this.secretAccessKey = secretAccessKey;
  }

}
