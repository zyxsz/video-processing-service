package com.zyx.vps.application.repositories;

import java.util.List;

import com.zyx.vps.application.entities.Video;

public interface VideosRepository {
  public List<Video> findByProjectId(String projectId);

  public void create(Video video);

  public void save(Video video);
}
