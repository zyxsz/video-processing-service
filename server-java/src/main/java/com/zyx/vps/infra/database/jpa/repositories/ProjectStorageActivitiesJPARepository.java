package com.zyx.vps.infra.database.jpa.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zyx.vps.application.dto.entities.ProjectStorageActivityDTO;
import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.application.repositories.ProjectStorageActivitiesRepository;
import com.zyx.vps.infra.database.jpa.entities.JPAProject;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageActivityJPA;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageJPA;
import com.zyx.vps.infra.database.jpa.mappers.ProjectStorageActivityMapper;

interface JPAProjectStorageActivitiesRepository extends JpaRepository<ProjectStorageActivityJPA, String> {

}

public class ProjectStorageActivitiesJPARepository implements ProjectStorageActivitiesRepository {

  @Autowired
  private JPAProjectStorageActivitiesRepository repository;

  @Override
  public void saveAll(List<ProjectStorageActivity> activities) {
    activities.stream().forEach(a -> {
      this.repository.save(ProjectStorageActivityMapper.toJPA(a));
    });
    // this.repository.saveAll(activities.stream().map(a ->
    // ProjectStorageActivityMapper.toJPA(a)).toList());
  }

  @Override
  public List<ProjectStorageActivity> findAllFromStorageId(String storageId) {
    ProjectStorageJPA storage = new ProjectStorageJPA(storageId);
    ProjectStorageActivityJPA activity = new ProjectStorageActivityJPA();

    activity.setStorage(storage);

    Example<ProjectStorageActivityJPA> example = Example.of(activity);

    List<ProjectStorageActivityJPA> activities = this.repository.findAll(example,
        Sort.by(Sort.Direction.DESC, "createdAt"));

    return activities.stream().map(a -> (ProjectStorageActivity) a).toList();
  }

}
