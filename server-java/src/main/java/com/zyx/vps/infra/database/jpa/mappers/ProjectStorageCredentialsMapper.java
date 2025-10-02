package com.zyx.vps.infra.database.jpa.mappers;

import com.zyx.vps.application.entities.ProjectStorageCredentials;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageCredentialsJPA;

public class ProjectStorageCredentialsMapper {

  public static ProjectStorageCredentialsJPA toJPA(ProjectStorageCredentials projectStorageCredentials) {

    if (projectStorageCredentials instanceof ProjectStorageCredentialsJPA)
      return (ProjectStorageCredentialsJPA) projectStorageCredentials;

    ProjectStorageCredentialsJPA projectStorageCredentialsJPA = new ProjectStorageCredentialsJPA(
        projectStorageCredentials.getId());

    if (projectStorageCredentials.getSymmetricEncryption() != null) {
      projectStorageCredentialsJPA
          .setSymmetricEncryption(SymmetricEncryptionMapper.toJPA(projectStorageCredentials.getSymmetricEncryption()));
    }

    projectStorageCredentialsJPA.setAccessKeyId(projectStorageCredentials.getAccessKeyId());
    projectStorageCredentialsJPA.setSecretAccessKey(projectStorageCredentials.getSecretAccessKey());
    projectStorageCredentialsJPA.setCreatedAt(projectStorageCredentials.getCreatedAt());
    projectStorageCredentialsJPA.setUpdatedAt(projectStorageCredentials.getUpdatedAt());

    return projectStorageCredentialsJPA;
  }

}
