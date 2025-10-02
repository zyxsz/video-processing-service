package com.zyx.vps.infra.http.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.zyx.vps.application.entities.Video;
import com.zyx.vps.application.services.VideosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/videos")
public class VideosController {
  @Autowired
  private VideosService videosService;

  @GetMapping("{projectId}")
  public List<Video> findVideosByProjectId(@PathVariable String projectId) {
    List<Video> videos = this.videosService.findVideosByProjectId(projectId);

    return videos;
  }

}
