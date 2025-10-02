package com.zyx.vps.application.repositories;

import com.zyx.vps.application.entities.ApiToken;

public interface ApiTokenRepository {
  public ApiToken findByToken(String token);

  public void create(ApiToken apiToken);

  public void save(ApiToken apiToken);

  public void delete(ApiToken apiToken);
}
