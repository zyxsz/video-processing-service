package com.zyx.vps.infra.database.jpa.mappers;

import com.zyx.vps.application.dto.entities.UserDTO;
import com.zyx.vps.application.entities.User;
import com.zyx.vps.infra.database.jpa.entities.JPAUser;

public class UserMapper {
  // public static User toUser(JPAUser jpaUser) {
  // User user = new User(jpaUser.getId(), jpaUser.getUsername(),
  // jpaUser.getEmail(),
  // jpaUser.isEmailVerifiedByProvider(), jpaUser.getAvatar(),
  // jpaUser.getProvider());

  // user.setUpdatedAt(jpaUser.getUpdatedAt());
  // user.setCreatedAt(jpaUser.getCreatedAt());

  // return user;
  // }

  public static JPAUser fromUser(User user) {
    JPAUser jpaUser = new JPAUser();

    jpaUser.setId(user.getId());
    jpaUser.setEmail(user.getEmail());
    jpaUser.setUsername(user.getUsername());
    jpaUser.setIsEmailVerifiedByProvider(user.isEmailVerifiedByProvider());
    jpaUser.setProvider(user.getProvider());
    jpaUser.setAvatar(user.getAvatar());
    jpaUser.setUpdatedAt(user.getUpdatedAt());
    jpaUser.setCreatedAt(user.getCreatedAt());

    return jpaUser;
  }

  public static JPAUser fromId(String userId) {
    JPAUser jpaUser = new JPAUser();
    jpaUser.setId(userId);

    return jpaUser;
  }

  public static UserDTO toDTO(User user) {
    UserDTO userDTO = new UserDTO();

    userDTO.setId(user.getId());
    userDTO.setAvatar(user.getAvatar());
    userDTO.setCreatedAt(user.getCreatedAt());
    userDTO.setEmail(user.getEmail());
    userDTO.setIsEmailVerifiedByProvider(user.isEmailVerifiedByProvider());
    userDTO.setProvider(user.getProvider());
    userDTO.setUpdatedAt(user.getUpdatedAt());
    userDTO.setUsername(user.getUsername());

    return userDTO;

  }

  public static JPAUser toJPA(User user) {
    if (user instanceof JPAUser)
      return (JPAUser) user;

    JPAUser jpaUser = new JPAUser();

    jpaUser.setId(user.getId());
    jpaUser.setEmail(user.getEmail());
    jpaUser.setUsername(user.getUsername());
    jpaUser.setIsEmailVerifiedByProvider(user.isEmailVerifiedByProvider());
    jpaUser.setProvider(user.getProvider());
    jpaUser.setAvatar(user.getAvatar());
    jpaUser.setUpdatedAt(user.getUpdatedAt());
    jpaUser.setCreatedAt(user.getCreatedAt());

    return jpaUser;
  }

}
