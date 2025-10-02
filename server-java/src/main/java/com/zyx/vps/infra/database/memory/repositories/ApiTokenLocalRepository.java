package com.zyx.vps.infra.database.memory.repositories;

import java.util.ArrayList;

import com.zyx.vps.application.entities.ApiToken;
import com.zyx.vps.application.repositories.ApiTokenRepository;

public class ApiTokenLocalRepository implements ApiTokenRepository {

  private ArrayList<ApiToken> apiTokens = new ArrayList<ApiToken>();

  @Override
  public ApiToken findByToken(String token) {
    for (ApiToken apiToken : this.apiTokens) {
      if (apiToken.getToken().equals(token)) {
        return apiToken;
      }
    }

    return null;
  }

  @Override
  public void create(ApiToken apiToken) {
    this.apiTokens.add(apiToken);
  }

  @Override
  public void save(ApiToken apiToken) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void delete(ApiToken apiToken) {
    this.apiTokens.removeIf(listApiToken -> listApiToken.getId().equals(apiToken.getId()));
  }

}
