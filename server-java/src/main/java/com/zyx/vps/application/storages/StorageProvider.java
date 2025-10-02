package com.zyx.vps.application.storages;

public interface StorageProvider {
  public String generateUploadSignedURL(String key, String contentType);
}
