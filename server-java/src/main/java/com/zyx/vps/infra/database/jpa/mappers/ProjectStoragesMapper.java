package com.zyx.vps.infra.database.jpa.mappers;

import com.zyx.vps.application.dto.entities.ProjectStorageDTO;
import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageJPA;

public class ProjectStoragesMapper {

  public static ProjectStorageDTO toDTO(ProjectStorage projectStorage) {
    ProjectStorageDTO projectStorageDTO = new ProjectStorageDTO();

    projectStorageDTO.setId(projectStorage.getId());
    projectStorageDTO.setRegion(projectStorage.getRegion());
    projectStorageDTO.setBucketName(projectStorage.getBucketName());
    projectStorageDTO.setStatus(projectStorage.getStatus());
    projectStorageDTO.setCreatedAt(projectStorage.getCreatedAt());
    projectStorageDTO.setUpdatedAt(projectStorage.getUpdatedAt());

    return projectStorageDTO;
  }

  public static ProjectStorageJPA toJPA(ProjectStorage projectStorage) {
    if (projectStorage instanceof ProjectStorageJPA)
      return (ProjectStorageJPA) projectStorage;

    ProjectStorageJPA projectStorageJPA = new ProjectStorageJPA(projectStorage.getId());

    projectStorageJPA.setBucketName(projectStorage.getBucketName());
    projectStorageJPA.setCreatedAt(projectStorage.getCreatedAt());
    projectStorageJPA.setRegion(projectStorage.getRegion());
    projectStorageJPA.setStatus(projectStorage.getStatus());
    projectStorageJPA.setUpdatedAt(projectStorage.getUpdatedAt());

    if (projectStorage.getProject() != null) {
      projectStorageJPA.setProject(ProjectMapper.toJPA(projectStorage.getProject()));
    }

    if (projectStorage.getActivities() != null) {
      projectStorageJPA.setActivities(projectStorage.getActivities());
    }

    // if (projectStorage.getCredentials() != null) {
    // projectStorageJPA.setCredentials(ProjectStorageCredentialsMapper.toJPA(projectStorage.getCredentials()));
    // }

    return projectStorageJPA;
  }

}
