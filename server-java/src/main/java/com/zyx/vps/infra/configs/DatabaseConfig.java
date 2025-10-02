package com.zyx.vps.infra.configs;

public class DatabaseConfig {
  private String driverClassName;
  private String url;
  private String username;
  private String password;

  public String getUrl() {
    return url;
  }

  public String getDriverClassName() {
    return driverClassName;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public void setDriverClassName(String driverClassName) {
    this.driverClassName = driverClassName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}