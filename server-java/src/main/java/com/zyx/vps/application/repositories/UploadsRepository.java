package com.zyx.vps.application.repositories;

import java.util.List;

import com.zyx.vps.application.entities.Upload;

public interface UploadsRepository {

  public List<Upload> findByProjectId(String projectId);

  public Upload findById(String id);

  public Upload findByKey(String key);

  public void create(Upload upload);

  public void save(Upload upload);

  public void delete(Upload upload);

}
