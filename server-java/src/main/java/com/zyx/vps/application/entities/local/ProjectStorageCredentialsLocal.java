package com.zyx.vps.application.entities.local;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.entities.ProjectStorageCredentials;
import com.zyx.vps.application.entities.SymmetricEncryption;

public class ProjectStorageCredentialsLocal implements ProjectStorageCredentials {
  private String id;
  private ProjectStorage storage;
  private SymmetricEncryption symmetricEncryption;

  private String accessKeyId;
  private String secretAccessKey;

  private Date updatedAt;
  private Date createdAt;

  public ProjectStorageCredentialsLocal(String id, SymmetricEncryption symmetricEncryption) {
    this.id = id != null ? id : UUID.randomUUID().toString();

    if (symmetricEncryption != null) {
      this.symmetricEncryption = symmetricEncryption;
    }

    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());
  }

  public String getId() {
    return id;
  }

  public ProjectStorage getProjectStorage() {
    return storage;
  }

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public String getSecretAccessKey() {
    return secretAccessKey;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String accessKeyId() {
    String decryptedAcceessKeyId = this.symmetricEncryption.decrypt(this.accessKeyId);

    return decryptedAcceessKeyId;
  }

  public String secretKeyId() {
    String decryptedSecretAccessKey = this.symmetricEncryption.decrypt(this.secretAccessKey);

    return decryptedSecretAccessKey;
  }

  public void setAccessKeyId(String accessKeyId) {
    try {
      String encryptedAccessKeyId = this.symmetricEncryption.encrypt(accessKeyId);

      this.accessKeyId = encryptedAccessKeyId;
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void setSecretAccessKey(String secretAccessKey) {
    try {
      String encryptedSecretAccessKey = this.symmetricEncryption.encrypt(secretAccessKey);
      this.secretAccessKey = encryptedSecretAccessKey;
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void setProjectStorage(ProjectStorage storage) {
    this.storage = storage;
  }

  public void setSymmetricEncryption(SymmetricEncryption symmetricEncryption) {
    this.symmetricEncryption = symmetricEncryption;
  }

  public SymmetricEncryption getSymmetricEncryption() {
    return symmetricEncryption;
  }

}