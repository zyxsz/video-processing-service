package com.zyx.vps.application.repositories;

import java.util.Optional;

import com.zyx.vps.application.entities.User;

public interface UsersRepository {

  public User findById(String id);

  public User findByEmail(String email);

  public User create(User user);

  public void save(User user);

  public void delete(User user);

}
