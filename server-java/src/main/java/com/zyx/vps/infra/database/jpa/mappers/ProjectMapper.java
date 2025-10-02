package com.zyx.vps.infra.database.jpa.mappers;

import com.zyx.vps.application.dto.entities.ProjectDTO;
import com.zyx.vps.application.entities.Project;
import com.zyx.vps.infra.database.jpa.entities.JPAProject;

public class ProjectMapper {
  public static JPAProject toJPA(Project project) {
    if (project instanceof JPAProject)
      return (JPAProject) project;

    JPAProject jpaProject = new JPAProject(project.getId());

    jpaProject.setName(project.getName());
    jpaProject.setUpdatedAt(project.getUpdatedAt());
    jpaProject.setCreatedAt(project.getCreatedAt());

    if (project.getUser() != null) {
      jpaProject.setUser(UserMapper.fromUser(project.getUser()));

    }

    return jpaProject;
  }

  public static ProjectDTO toDTO(Project project) {
    ProjectDTO projectDTO = new ProjectDTO();

    projectDTO.setId(project.getId());
    projectDTO.setName(project.getName());
    projectDTO.setCreatedAt(project.getCreatedAt());
    projectDTO.setUpdatedAt(project.getUpdatedAt());

    return projectDTO;
  }
}
