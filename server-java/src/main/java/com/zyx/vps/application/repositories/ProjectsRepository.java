package com.zyx.vps.application.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.zyx.vps.application.entities.Project;

public interface ProjectsRepository {

  public void validateProjectByUserId(String projectId, String userId);

  public List<Project> findByNameAndUserId(String name, String userId);

  public int countUserProjects(String userId);

  public Project findById(String id);

  public Project findByIdAndUserId(String id, String userId);

  public List<Project> findByUserId(String userId, Sort sort);

  public void create(Project project);

  public void save(Project project);

  public void delete(Project project);

}
