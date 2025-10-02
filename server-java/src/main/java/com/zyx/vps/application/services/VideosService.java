package com.zyx.vps.application.services;

import java.util.List;

import com.zyx.vps.application.entities.Video;
import com.zyx.vps.application.repositories.VideosRepository;

public class VideosService {
  private VideosRepository videosRepository;

  public VideosService(VideosRepository videosRepository) {
    this.videosRepository = videosRepository;
  }

  public List<Video> findVideosByProjectId(String projectId) {
    List<Video> videos = this.videosRepository.findByProjectId(projectId);

    return videos;
  }
}
