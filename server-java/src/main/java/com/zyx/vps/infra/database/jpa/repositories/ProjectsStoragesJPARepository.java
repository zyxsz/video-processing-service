package com.zyx.vps.infra.database.jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.repositories.ProjectStoragesRepository;
import com.zyx.vps.application.repositories.SymmetricEncryptionRepository;
import com.zyx.vps.infra.database.jpa.entities.JPAProject;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageCredentialsJPA;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageJPA;
import com.zyx.vps.infra.database.jpa.entities.SymmetricEncryptionJPA;
import com.zyx.vps.infra.database.jpa.mappers.ProjectStorageCredentialsMapper;
import com.zyx.vps.infra.database.jpa.mappers.ProjectStoragesMapper;
import com.zyx.vps.infra.database.jpa.mappers.SymmetricEncryptionMapper;

interface JPAProjectsStoragesRepository extends JpaRepository<ProjectStorageJPA, String> {
  ProjectStorageJPA save(ProjectStorageJPA user);

}

interface JPAProjectsStorageCredentialsRepository extends JpaRepository<ProjectStorageCredentialsJPA, String> {
  ProjectStorageCredentialsJPA save(ProjectStorageCredentialsJPA projectStorageCredentialsJPA);
}

public class ProjectsStoragesJPARepository implements ProjectStoragesRepository {

  @Autowired
  private JPAProjectsStoragesRepository repository;

  @Autowired
  private JPAProjectsStorageCredentialsRepository credentialsRepository;

  @Autowired
  private SymmetricEncryptionRepository symmetricEncryptionsRepository;

  @Override
  public void create(ProjectStorage projectStorage) {
    ProjectStorageJPA projectStorageJPA = ProjectStoragesMapper.toJPA(projectStorage);

    ProjectStorageCredentialsJPA credentials = ProjectStorageCredentialsMapper.toJPA(projectStorage.getCredentials());
    SymmetricEncryptionJPA symmetricEncryption = credentials.getSymmetricEncryption();

    credentials.setProjectStorage(projectStorageJPA);
    credentials.setSymmetricEncryption(null);
    this.credentialsRepository.save(credentials);

    symmetricEncryption.setCredentials(credentials);
    this.symmetricEncryptionsRepository.create(symmetricEncryption);

    this.repository.save(projectStorageJPA);
  }

  @Override
  public ProjectStorage findByProjectIdAndBucketName(String projectId, String bucketName) {
    JPAProject jpaProject = new JPAProject(projectId);
    ProjectStorageJPA projectStorageJpaExample = new ProjectStorageJPA();

    projectStorageJpaExample.setProject(jpaProject);
    projectStorageJpaExample.setBucketName(bucketName);

    ExampleMatcher matcher = ExampleMatcher.matching()
        .withMatcher("projectId", m -> m.ignoreCase())
        .withMatcher("bucketName", m -> m.ignoreCase());

    Example<ProjectStorageJPA> example = Example.of(projectStorageJpaExample, matcher);

    Optional<ProjectStorageJPA> projectStorage = this.repository.findOne(example);

    if (projectStorage.isEmpty())
      return null;

    return projectStorage.get();
  }

  @Override
  public List<ProjectStorage> findAllByProjectId(String projectId) {
    JPAProject jpaProject = new JPAProject(projectId);
    ProjectStorageJPA projectStorageJpaExample = new ProjectStorageJPA();

    projectStorageJpaExample.setProject(jpaProject);

    Example<ProjectStorageJPA> example = Example.of(projectStorageJpaExample);

    List<ProjectStorageJPA> projectStorages = this.repository.findAll(example);

    return projectStorages.stream().map(p -> (ProjectStorage) p).toList();
  }

  @Override
  public ProjectStorage findProjectStorageById(String id, String projectId) {
    ProjectStorageJPA projectStorageJpaExample = new ProjectStorageJPA();

    projectStorageJpaExample.setId(id);
    projectStorageJpaExample.setProject(new JPAProject(projectId));

    Example<ProjectStorageJPA> example = Example.of(projectStorageJpaExample);

    Optional<ProjectStorageJPA> projectStorage = this.repository.findOne(example);

    if (projectStorage.isEmpty())
      return null;

    return projectStorage.get();
  }

}
