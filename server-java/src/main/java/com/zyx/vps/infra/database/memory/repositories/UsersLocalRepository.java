package com.zyx.vps.infra.database.memory.repositories;

import java.util.ArrayList;
import java.util.Optional;

import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.repositories.UsersRepository;

public class UsersLocalRepository implements UsersRepository {

  private ArrayList<User> users = new ArrayList<User>();

  @Override
  public User findById(String id) {

    for (User user : this.users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }

    return null;
    // throw new Error("User not found");
  }

  @Override
  public User findByEmail(String email) {
    for (User user : this.users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        return user;
      }
    }

    return null;
  }

  @Override
  public User create(User user) {
    this.users.add(user);

    return user;
  }

  @Override
  public void save(User user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public void delete(User user) {
    this.users.removeIf(listUser -> listUser.getId().equals(user.getId()));
  }

}
