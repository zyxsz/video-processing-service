package com.zyx.vps.application.services;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.entities.local.ProjectLocal;
import com.zyx.vps.application.errors.BadRequestException;
import com.zyx.vps.application.errors.NotfoundException;
import com.zyx.vps.application.repositories.ProjectsRepository;

public class ProjectsService {
  private ProjectsRepository projectsRepository;

  public ProjectsService(ProjectsRepository projectsRepository) {
    this.projectsRepository = projectsRepository;
  }

  public Project findProject(String id, String userId) {
    Project project = this.projectsRepository.findByIdAndUserId(id, userId);

    return project;
  }

  public Project getProject(String id, String userId) {
    Project project = this.projectsRepository.findById(id);

    if (project == null)
      throw new NotfoundException("Project not found");

    if (!project.getUser().getId().equalsIgnoreCase(userId))
      throw new NotfoundException("Permission not found");

    return project;
  }

  public List<Project> getUserProjects(String userId) {
    List<Project> projects = this.projectsRepository.findByUserId(userId, Sort.by(Sort.Direction.ASC, "createdAt"));

    return projects;
  }

  public Project createProject(String name, User user) {
    Boolean projectAlreadyExists = this.projectsRepository.findByNameAndUserId(name, user.getId()).size() > 0;

    if (projectAlreadyExists)
      throw new BadRequestException("Project already exists");

    int userProjectsCount = this.projectsRepository.countUserProjects(user.getId());

    if (userProjectsCount >= 2)
      throw new BadRequestException("Too many projects");

    Project project = new ProjectLocal(null, name, user);

    this.projectsRepository.create(project);

    return project;

  }
}
