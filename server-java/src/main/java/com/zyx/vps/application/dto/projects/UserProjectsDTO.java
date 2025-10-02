package com.zyx.vps.application.dto.projects;

import java.util.List;

import com.zyx.vps.application.entities.Project;

public class UserProjectsDTO {
  private List<Project> projects;

  public UserProjectsDTO(List<Project> projects) {
    this.projects = projects;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

}
