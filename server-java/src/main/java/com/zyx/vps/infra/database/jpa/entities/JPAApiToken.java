package com.zyx.vps.infra.database.jpa.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.zyx.vps.application.entities.ApiToken;
import com.zyx.vps.application.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "apiTokens")
public class JPAApiToken implements ApiToken {
  @Id
  // @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true)
  private String token;

  @ColumnDefault("true")
  private Boolean enabled;

  private Date expiresAt;

  @LastModifiedDate
  private Date updatedAt;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Date createdAt;

  @ManyToOne()
  @JoinColumn(name = "userId", referencedColumnName = "id")
  private JPAUser user;

  public String getId() {
    return id;
  }

  public String getToken() {
    return token;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public JPAUser getUser() {
    return user;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setUser(User user) {
    this.user = (JPAUser) user;
  }

  @Override
  public Boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public Boolean isExpired() {
    return this.expiresAt.before(Timestamp.from(Instant.now()));
  }
}
