package com.zyx.vps.infra.database.jpa.mappers;

import com.zyx.vps.application.entities.ApiToken;
import com.zyx.vps.infra.database.jpa.entities.JPAApiToken;

public class ApiTokenMapper {
  // public static ApiToken toApiToken(JPAApiToken jpaApiToken) {
  // ApiToken apiToken = new ApiToken(jpaApiToken.getId(),
  // jpaApiToken.getUser().getId(), jpaApiToken.getToken(),
  // jpaApiToken.getExpiresAt());

  // apiToken.setEnabled(jpaApiToken.getEnabled());
  // apiToken.setUpdatedAt(jpaApiToken.getUpdatedAt());

  // apiToken.setCreatedAt(jpaApiToken.getCreatedAt());

  // return apiToken;
  // }

  public static JPAApiToken fromApiToken(ApiToken apiToken) {
    JPAApiToken jpaApiToken = new JPAApiToken();

    jpaApiToken.setId(apiToken.getId());
    jpaApiToken.setToken(apiToken.getToken());
    jpaApiToken.setEnabled(apiToken.isEnabled());
    jpaApiToken.setExpiresAt(apiToken.getExpiresAt());
    jpaApiToken.setUpdatedAt(apiToken.getUpdatedAt());
    jpaApiToken.setCreatedAt(apiToken.getCreatedAt());

    if (apiToken.getUser() != null) {
      jpaApiToken.setUser(UserMapper.fromUser(apiToken.getUser()));
    }

    return jpaApiToken;

  }
}
