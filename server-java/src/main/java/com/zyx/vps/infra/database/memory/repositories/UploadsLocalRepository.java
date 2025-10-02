package com.zyx.vps.infra.database.memory.repositories;

import java.util.ArrayList;
import java.util.List;

import com.zyx.vps.application.entities.Upload;
import com.zyx.vps.application.repositories.UploadsRepository;

public class UploadsLocalRepository implements UploadsRepository {

  private List<Upload> uploads = new ArrayList<Upload>();

  @Override
  public List<Upload> findByProjectId(String projectId) {
    return this.uploads.stream().filter(upload -> upload.getProject().getId().equals(projectId)).toList();
  }

  @Override
  public Upload findByKey(String key) {
    // for (Upload upload : this.uploads) {
    // if (upload.getKey().equals(id)) {
    // return upload;
    // }
    // }

    // this.uploads.index

    return this.uploads.stream()
        .filter(upload -> upload.getKey().equals(key))
        .findAny()
        .orElse(null);
  }

  @Override
  public Upload findById(String id) {
    for (Upload upload : this.uploads) {
      if (upload.getId().equals(id)) {
        return upload;
      }
    }

    return null;
  }

  @Override
  public void create(Upload upload) {
    this.uploads.add(upload);
  }

  @Override
  public void save(Upload upload) {
    this.uploads.removeIf(listUpload -> listUpload.getId().equals(upload.getId()));
    this.uploads.add(upload);
  }

  @Override
  public void delete(Upload upload) {
    this.uploads.removeIf(listUpload -> listUpload.getId().equals(upload.getId()));
  }

}
