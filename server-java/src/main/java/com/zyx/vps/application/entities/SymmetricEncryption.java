package com.zyx.vps.application.entities;

public interface SymmetricEncryption {

  public String getId();

  public void setId(String id);

  public String encrypt(String data)
      throws Exception;

  public String decrypt(String data);

  public String getSecretKey();

  public String getIv();

  public ProjectStorageCredentials getCredentials();
}
