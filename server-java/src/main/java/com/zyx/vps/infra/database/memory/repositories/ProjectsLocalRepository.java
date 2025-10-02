package com.zyx.vps.infra.database.memory.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;

import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.repositories.ProjectsRepository;

public class ProjectsLocalRepository implements ProjectsRepository {

  private ArrayList<Project> projects = new ArrayList<Project>();

  @Override
  public Project findById(String id) {
    for (Project project : this.projects) {
      if (project.getId().equals(id)) {
        return project;
      }
    }

    return null;
  }

  @Override
  public List<Project> findByUserId(String userId, Sort sort) {
    List<Project> userProjects = this.projects.stream().distinct().collect(Collectors.toList());
    ;

    userProjects.removeIf(project -> !project.getUser().getId().equals(userId));

    return userProjects;
  }

  @Override
  public List<Project> findByNameAndUserId(String name, String userId) {
    List<Project> userProjects = this.projects.stream().distinct().collect(Collectors.toList());
    ;

    userProjects
        .removeIf(project -> !project.getUser().getId().equals(userId) && !project.getName().equalsIgnoreCase(name));

    return userProjects;
  }

  // @Override
  // public Project find(String id) {

  // for (Project project : this.projects) {
  // if (project.getId().equals(id)) {
  // return project;
  // }
  // }

  // return null;
  // }

  @Override
  public void create(Project project) {
    this.projects.add(project);
  }

  @Override
  public void save(Project project) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void delete(Project project) {
    this.projects.removeIf(listProject -> listProject.getId().equals(project.getId()));
  }

  @Override
  public int countUserProjects(String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'countUserProjects'");
  }

  @Override
  public Project findByIdAndUserId(String id, String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByIdAndUserId'");
  }

  @Override
  public void validateProjectByUserId(String projectId, String userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'validateProjectByUserId'");
  }

}
