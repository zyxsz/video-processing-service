package com.zyx.vps.application.entities;

import java.util.Date;

public interface ProjectStorageCredentials {

  public String getId();

  public ProjectStorage getProjectStorage();

  public SymmetricEncryption getSymmetricEncryption();

  public String getAccessKeyId();

  public String getSecretAccessKey();

  public Date getUpdatedAt();

  public Date getCreatedAt();

  public void setUpdatedAt(Date updatedAt);

  public void setCreatedAt(Date createdAt);

  public void setId(String id);

  public void setAccessKeyId(String accessKeyId);

  public void setSecretAccessKey(String secretAccessKey);

  public void setProjectStorage(ProjectStorage projectStorage);

  public void setSymmetricEncryption(SymmetricEncryption symmetricEncryption);

}
