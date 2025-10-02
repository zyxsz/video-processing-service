package com.zyx.vps.infra.database.jpa.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.repositories.UsersRepository;
import com.zyx.vps.infra.database.jpa.entities.JPAUser;
import com.zyx.vps.infra.database.jpa.mappers.UserMapper;

interface JPAUsersRepository extends JpaRepository<JPAUser, String> {
  JPAUser save(JPAUser user);

  Optional<JPAUser> findByEmail(String email);
}

public class UsersJPARepository implements UsersRepository {

  @Autowired
  private JPAUsersRepository repository;

  @Override
  public User findById(String id) {
    Optional<JPAUser> jpaUser = this.repository.findById(id);

    if (jpaUser.isEmpty())
      return null;

    return jpaUser.get();
  }

  @Override
  public User findByEmail(String email) {
    Optional<JPAUser> jpaUser = this.repository.findByEmail(email);

    if (jpaUser.isEmpty())
      return null;

    return jpaUser.get();
  }

  @Override
  public User create(User user) {
    JPAUser jpaUser = UserMapper.fromUser(user);

    this.repository.save(jpaUser);

    return user;
  }

  @Override
  public void save(User user) {
    this.repository.save((JPAUser) user);
  }

  @Override
  public void delete(User user) {
    this.repository.deleteById(user.getId());
  }

}
