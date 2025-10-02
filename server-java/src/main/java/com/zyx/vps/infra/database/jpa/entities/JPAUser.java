package com.zyx.vps.infra.database.jpa.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.zyx.vps.application.entities.User;
import com.zyx.vps.application.enums.OAuthProvider;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "user")
@Table(name = "users")
public class JPAUser implements User {

  @Id
  // @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @ColumnDefault("false")
  private Boolean isEmailVerifiedByProvider;

  @Nullable
  private String avatar;

  @Enumerated(EnumType.STRING)
  private OAuthProvider provider;

  @LastModifiedDate
  private Date updatedAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Date createdAt;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<JPAApiToken> apiTokens;

  // @OneToMany(targetEntity = JPAApiToken.class, cascade = CascadeType.ALL, fetch
  // = FetchType.LAZY)
  // private List<JPAApiToken> apiTokens;

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getAvatar() {
    return avatar;
  }

  public Boolean isEmailVerifiedByProvider() {
    return isEmailVerifiedByProvider;
  }

  public OAuthProvider getProvider() {
    return provider;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setIsEmailVerifiedByProvider(Boolean isEmailVerifiedByProvider) {
    this.isEmailVerifiedByProvider = isEmailVerifiedByProvider;
  }

  public void setProvider(OAuthProvider provider) {
    this.provider = provider;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<JPAApiToken> getApiTokens() {
    return apiTokens;
  }
}
