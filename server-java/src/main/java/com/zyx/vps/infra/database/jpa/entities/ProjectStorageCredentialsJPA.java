package com.zyx.vps.infra.database.jpa.entities;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.entities.ProjectStorageCredentials;
import com.zyx.vps.application.entities.SymmetricEncryption;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "projectStorageCredentials")
@Table(name = "projectsStoragesCredentials")
public class ProjectStorageCredentialsJPA implements ProjectStorageCredentials {
  @Id
  private String id;

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "projectStorageId", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ProjectStorageJPA storage;

  @OneToOne(mappedBy = "credentials", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private SymmetricEncryptionJPA symmetricEncryption;

  private String accessKeyId;
  private String secretAccessKey;

  @LastModifiedDate
  private Date updatedAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Date createdAt;

  public ProjectStorageCredentialsJPA() {
  }

  public ProjectStorageCredentialsJPA(String id) {
    this.id = id != null ? id : UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public ProjectStorage getProjectStorage() {
    return this.storage;
  }

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public String getSecretAccessKey() {
    return secretAccessKey;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  public Date getCreatedAt() {
    return this.createdAt;
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

  public void setProjectStorage(ProjectStorage project) {
    this.storage = (ProjectStorageJPA) project;
  }

  public void setSymmetricEncryption(SymmetricEncryption symmetricEncryption) {
    this.symmetricEncryption = (SymmetricEncryptionJPA) symmetricEncryption;
  }

  public SymmetricEncryptionJPA getSymmetricEncryption() {
    return symmetricEncryption;
  }

}
