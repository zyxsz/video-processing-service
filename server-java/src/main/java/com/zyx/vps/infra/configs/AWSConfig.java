package com.zyx.vps.infra.configs;

public class AWSConfig {

  private String accessKeyId;
  private String secretAccessKey;
  private String sessionToken;

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public String getSecretAccessKey() {
    return secretAccessKey;
  }

  public String getSessionToken() {
    return sessionToken;
  }

  public void setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
  }

  public void setSecretAccessKey(String secretAccessKey) {
    this.secretAccessKey = secretAccessKey;
  }

  public void setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
  }

}
