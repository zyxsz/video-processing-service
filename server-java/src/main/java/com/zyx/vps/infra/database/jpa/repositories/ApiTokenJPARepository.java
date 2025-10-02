package com.zyx.vps.infra.database.jpa.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zyx.vps.application.entities.ApiToken;
import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.repositories.ApiTokenRepository;
import com.zyx.vps.infra.database.jpa.entities.JPAApiToken;
import com.zyx.vps.infra.database.jpa.entities.JPAUser;
import com.zyx.vps.infra.database.jpa.mappers.ApiTokenMapper;
import com.zyx.vps.infra.database.jpa.mappers.UserMapper;

interface JPAApiTokenRepository extends JpaRepository<JPAApiToken, String> {
  Optional<JPAApiToken> findByToken(String token);
}

public class ApiTokenJPARepository implements ApiTokenRepository {

  @Autowired
  private JPAApiTokenRepository repository;

  @Override
  public ApiToken findByToken(String token) {
    Optional<JPAApiToken> jpaApiToken = this.repository.findByToken(token);

    if (jpaApiToken.isEmpty())
      return null;

    ApiToken apiToken = jpaApiToken.get();

    return apiToken;
  }

  @Override
  public void create(ApiToken apiToken) {
    JPAApiToken jpaApiToken = ApiTokenMapper.fromApiToken(apiToken);

    this.repository.save(jpaApiToken);

  }

  @Override
  public void save(ApiToken apiToken) {
    JPAApiToken jpaApiToken = ApiTokenMapper.fromApiToken(apiToken);

    this.repository.save(jpaApiToken);
  }

  @Override
  public void delete(ApiToken apiToken) {
    JPAApiToken jpaApiToken = ApiTokenMapper.fromApiToken(apiToken);

    this.repository.delete(jpaApiToken);
  }

}
