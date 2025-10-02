package com.zyx.vps.infra.http.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyx.vps.application.dto.uploads.CreateUploadDTO;
import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.Upload;
import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.errors.NotfoundException;
import com.zyx.vps.application.services.ProjectsService;
import com.zyx.vps.application.services.UploadsService;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedRoute;
import com.zyx.vps.infra.http.interceptors.annotations.AuthenticatedUser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/uploads")
public class UploadsController {

  @Autowired
  private UploadsService uploadsService;

  @Autowired
  private ProjectsService projectsService;

  @GetMapping("{projectId}")
  @AuthenticatedRoute
  public List<Upload> findUploadsByProjectId(@PathVariable String projectId) {
    List<Upload> uploads = this.uploadsService.findUploadsByProjectId(projectId);

    return uploads;
  }

  @PostMapping
  @AuthenticatedRoute
  public Upload createUpload(@RequestBody CreateUploadDTO body, @AuthenticatedUser User user) {
    Project project = this.projectsService.findProject(body.getProjectId(), user.getId());

    if (project == null)
      throw new NotfoundException("Project not found");

    Upload upload = this.uploadsService.create(project.getId());

    return upload;
  }

}
