package com.zyx.vps.infra.http.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyx.vps.application.dto.entities.ProjectDTO;
import com.zyx.vps.application.dto.projects.CreateProjectDTO;
import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.services.ProjectsService;
import com.zyx.vps.infra.database.jpa.mappers.ProjectMapper;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedRoute;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedUser;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

  @Autowired
  private ProjectsService projectsService;

  @GetMapping
  @AuthenticatedRoute
  public List<ProjectDTO> getUserProjects(@AuthenticatedUser User user) {
    List<Project> userProjects = this.projectsService.getUserProjects(user.getId());

    return userProjects.stream().map(project -> ProjectMapper.toDTO(project)).toList();
  }

  @GetMapping("{id}")
  @AuthenticatedRoute
  public ProjectDTO getProject(@PathVariable String id, @AuthenticatedUser User user) {
    Project project = this.projectsService.getProject(id, user.getId());

    return ProjectMapper.toDTO(project);
  }

  @PostMapping
  @AuthenticatedRoute
  public ProjectDTO createProject(@RequestBody CreateProjectDTO body, @AuthenticatedUser User user) {
    Project project = this.projectsService.createProject(body.getName(), user);

    return ProjectMapper.toDTO(project);
  }

}
