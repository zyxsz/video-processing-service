package com.zyx.vps.infra.database.memory.repositories;

import java.util.ArrayList;
import java.util.List;

import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.repositories.ProjectStoragesRepository;

public class ProjectStoragesLocalRepository implements ProjectStoragesRepository {

  private List<ProjectStorage> projectStorages = new ArrayList<ProjectStorage>();

  @Override
  public void create(ProjectStorage projectStorage) {
    this.projectStorages.add(projectStorage);
  }

  @Override
  public List<ProjectStorage> findAllByProjectId(String projectId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAllByProjectId'");
  }

  @Override
  public ProjectStorage findByProjectIdAndBucketName(String projectId, String bucketName) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByProjectIdAndBucketName'");
  }

  @Override
  public ProjectStorage findProjectStorageById(String id, String projectId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findProjectStorageById'");
  }

}
