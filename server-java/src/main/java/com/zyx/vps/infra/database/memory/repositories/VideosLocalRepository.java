package com.zyx.vps.infra.database.memory.repositories;

import java.util.ArrayList;
import java.util.List;

import com.zyx.vps.application.entities.Video;
import com.zyx.vps.application.repositories.VideosRepository;

public class VideosLocalRepository implements VideosRepository {
  private ArrayList<Video> videos = new ArrayList<Video>();

  @Override
  public List<Video> findByProjectId(String projectId) {
    return this.videos.stream().filter(video -> video.getProject().getId().equals(projectId)).toList();
  }

  @Override
  public void create(Video video) {
    this.videos.add(video);
  }

  @Override
  public void save(Video video) {
    this.videos.removeIf(listUpload -> listUpload.getId().equals(video.getId()));
    this.videos.add(video);
  }

}
