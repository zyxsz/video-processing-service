package com.zyx.vps.application.repositories;

import java.util.List;

import com.zyx.vps.application.entities.ProjectStorageActivity;

public interface ProjectStorageActivitiesRepository {

  public void saveAll(List<ProjectStorageActivity> activities);

  public List<ProjectStorageActivity> findAllFromStorageId(String storageId);
}
