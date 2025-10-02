package com.zyx.vps.application.repositories;

import java.util.List;

import com.zyx.vps.application.entities.ProjectStorage;

public interface ProjectStoragesRepository {
  public void create(ProjectStorage projectStorage);

  public List<ProjectStorage> findAllByProjectId(String projectId);

  public ProjectStorage findByProjectIdAndBucketName(String projectId, String bucketName);

  public ProjectStorage findProjectStorageById(String id, String projectId);

}
