package com.zyx.vps.infra.database.jpa.mappers;

import com.zyx.vps.application.dto.entities.ProjectStorageActivityDTO;
import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageActivityJPA;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageJPA;

public class ProjectStorageActivityMapper {
  public static ProjectStorageActivityDTO toDTO(ProjectStorageActivity projectStorageActivity) {
    ProjectStorageActivityDTO projectStorageActivityDTO = new ProjectStorageActivityDTO();

    projectStorageActivityDTO.setId(projectStorageActivity.getId());
    projectStorageActivityDTO.setOperationData(projectStorageActivity.getOperationData());
    projectStorageActivityDTO.setOperationType(projectStorageActivity.getOperationType());
    projectStorageActivityDTO.setExecutedAt(projectStorageActivity.getExecutedAt());
    projectStorageActivityDTO.setUpdatedAt(projectStorageActivity.getUpdatedAt());
    projectStorageActivityDTO.setCreatedAt(projectStorageActivity.getCreatedAt());

    return projectStorageActivityDTO;
  }

  public static ProjectStorageActivityJPA toJPA(ProjectStorageActivity projectStorageActivity) {
    if (projectStorageActivity instanceof ProjectStorageActivityJPA)
      return (ProjectStorageActivityJPA) projectStorageActivity;

    ProjectStorageActivityJPA projectStorageActivityJPA = new ProjectStorageActivityJPA();

    projectStorageActivityJPA.setId(projectStorageActivity.getId());

    projectStorageActivityJPA.setOperationData(projectStorageActivity.getOperationData());
    projectStorageActivityJPA.setOperationType(projectStorageActivity.getOperationType());

    projectStorageActivityJPA.setExecutedAt(projectStorageActivity.getExecutedAt());
    projectStorageActivityJPA.setUpdatedAt(projectStorageActivity.getUpdatedAt());
    projectStorageActivityJPA.setCreatedAt(projectStorageActivity.getCreatedAt());

    if (projectStorageActivity.getStorage() != null) {
      projectStorageActivityJPA.setStorage(new ProjectStorageJPA(projectStorageActivity.getStorage().getId()));
    }

    return projectStorageActivityJPA;
  }
}
