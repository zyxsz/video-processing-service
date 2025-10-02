package com.zyx.vps.infra.http.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyx.vps.application.dto.entities.ProjectStorageActivityDTO;
import com.zyx.vps.application.dto.entities.ProjectStorageDTO;
import com.zyx.vps.application.dto.projects.CreateProjectStorageDTO;
import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.errors.NotfoundException;
import com.zyx.vps.application.repositories.ProjectsRepository;
import com.zyx.vps.application.services.ProjectStoragesService;
import com.zyx.vps.infra.database.jpa.mappers.ProjectStorageActivityMapper;
import com.zyx.vps.infra.database.jpa.mappers.ProjectStoragesMapper;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedRoute;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedUser;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/projects/{projectId}/storages")
public class ProjectStoragesController {

  @Autowired
  private ProjectsRepository projectsRepository;

  @Autowired
  private ProjectStoragesService projectStoragesService;

  @GetMapping
  @AuthenticatedRoute
  public List<ProjectStorageDTO> findProjectStoragesByProjectId(@PathVariable String projectId,
      @AuthenticatedUser User user) {
    this.projectsRepository.validateProjectByUserId(projectId, user.getId());

    List<ProjectStorage> projectStorages = this.projectStoragesService.findProjectStoragesByProjectId(projectId);

    return projectStorages.stream().map(p -> ProjectStoragesMapper.toDTO(p)).toList();
  }

  @GetMapping("{storageId}")
  @AuthenticatedRoute
  public ProjectStorageDTO findProjectStoragesByProjectId(@PathVariable String projectId,
      @PathVariable String storageId,
      @AuthenticatedUser User user) {
    this.projectsRepository.validateProjectByUserId(projectId, user.getId());

    ProjectStorage projectStorage = this.projectStoragesService.findStorageById(storageId, projectId);

    if (projectStorage == null)
      throw new NotfoundException("Project not found");

    ProjectStorageDTO projectStorageDTO = ProjectStoragesMapper.toDTO(projectStorage);

    // if (projectStorage.getActivities().size() > 0) {
    // projectStorageDTO.setActivities(
    // projectStorage.getActivities().stream().map(a ->
    // ProjectStorageActivityMapper.toDTO(a)).toList());
    // }

    return projectStorageDTO;
  }

  @PostMapping
  @AuthenticatedRoute
  public ProjectStorageDTO createProjectStorage(@PathVariable String projectId,
      @Valid @RequestBody CreateProjectStorageDTO body, @AuthenticatedUser User user) {
    ProjectStorage projectStorage = this.projectStoragesService.create(projectId, body, user.getId());

    return ProjectStoragesMapper.toDTO(projectStorage);
  }

  @GetMapping("{storageId}/activities")
  @AuthenticatedRoute
  public List<ProjectStorageActivityDTO> findProjectStorageActivities(@PathVariable String projectId,
      @PathVariable String storageId,
      @AuthenticatedUser User user) {

    List<ProjectStorageActivity> activities = this.projectStoragesService.findActivities(user.getId(), projectId,
        storageId);

    return activities.stream().map(a -> ProjectStorageActivityMapper.toDTO(a)).toList();
  }

}
