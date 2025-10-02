package com.zyx.vps.infra.database.jpa.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.application.entities.ProjectStorageCredentials;
import com.zyx.vps.application.enums.ProjectStoreStatus;
import com.zyx.vps.infra.database.jpa.mappers.ProjectStorageActivityMapper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "projectStorage")
@Table(name = "projectsStorages")
public class ProjectStorageJPA implements ProjectStorage {

  @Id
  private String id;

  private String region;
  private String bucketName;

  @OneToOne(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
  private ProjectStorageCredentialsJPA credentials;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "projectId", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private JPAProject project;

  @Enumerated(EnumType.STRING)
  private ProjectStoreStatus status;

  @LastModifiedDate
  private Timestamp updatedAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Timestamp createdAt;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "storage", cascade = CascadeType.ALL)
  private List<ProjectStorageActivityJPA> activities;

  public ProjectStorageJPA() {
  }

  public ProjectStorageJPA(String id) {
    this.id = id != null ? id : UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public String getBucketName() {
    return bucketName;
  }

  public String getRegion() {
    return region;
  }

  public ProjectStoreStatus getStatus() {
    return status;
  }

  public JPAProject getProject() {
    return project;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setStatus(ProjectStoreStatus status) {
    this.status = status;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setProject(Project project) {
    this.project = (JPAProject) project;
    // this.project = (J)
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public void setCredentials(ProjectStorageCredentials credentials) {
    this.credentials = (ProjectStorageCredentialsJPA) credentials;
  }

  @Override
  public ProjectStorageCredentials getCredentials() {
    return this.credentials;
  }

  public List<ProjectStorageActivity> getActivities() {
    return activities.stream().map(a -> (ProjectStorageActivity) a).toList();
  }

  public void setActivities(List<ProjectStorageActivity> activities) {
    this.activities = activities.stream().map(a -> ProjectStorageActivityMapper.toJPA(a)).toList();
  }

}
