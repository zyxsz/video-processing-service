package com.zyx.vps.infra.database.jpa.entities;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.zyx.vps.application.entities.SymmetricEncryption;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "symmetricEncryption")
@Table(name = "symmetricEncryption")
public class SymmetricEncryptionJPA implements SymmetricEncryption {

  @Id
  private String id;

  @Transient
  private String algorithm = "AES";

  @Transient
  private KeyGenerator keygenerator;

  @Transient
  private SecretKey key;
  @Transient
  private IvParameterSpec iv;

  @Column(name = "secretKey")
  private String secretKeyField;
  @Column(name = "iv")
  private String ivField;

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "credentialsId", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private ProjectStorageCredentialsJPA credentials;

  public SymmetricEncryptionJPA() {
  }

  public SymmetricEncryptionJPA(String secretKey, String iv) {
    this.id = id != null ? id : UUID.randomUUID().toString();

    try {
      this.keygenerator = KeyGenerator.getInstance(this.algorithm);

      if (secretKey != null) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        this.key = new SecretKeySpec(decodedKey, 0, decodedKey.length, this.algorithm);
      } else {
        this.key = keygenerator.generateKey();
      }

      if (iv != null) {
        byte[] decodedIv = Base64.getDecoder().decode(iv);
        this.iv = new IvParameterSpec(decodedIv);
        this.ivField = iv;
      } else {
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);

        this.iv = new IvParameterSpec(initializationVector);
        this.ivField = Base64.getEncoder().encodeToString(initializationVector);
      }

      this.secretKeyField = Base64.getEncoder().encodeToString(this.key.getEncoded());

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

  }

  public String encrypt(String data)
      throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    byte[] cipherText = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

    return Base64.getEncoder()
        .encodeToString(cipherText);
  }

  public String decrypt(String data) {
    try {
      Cipher cipher;
      cipher = Cipher.getInstance("AES/CFB8/NoPadding");
      cipher.init(Cipher.DECRYPT_MODE, key, iv);
      byte[] plainText = cipher.doFinal(Base64.getDecoder()
          .decode(data));

      return new String(plainText);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCredentials(ProjectStorageCredentialsJPA credentials) {
    this.credentials = credentials;
  }

  @PostLoad
  private void postLoad() {
    try {
      this.keygenerator = KeyGenerator.getInstance(this.algorithm);

      byte[] decodedKey = Base64.getDecoder().decode(this.secretKeyField);
      this.key = new SecretKeySpec(decodedKey, 0, decodedKey.length, this.algorithm);

      if (this.ivField != null) {
        byte[] decodedIv = Base64.getDecoder().decode(this.ivField);
        this.iv = new IvParameterSpec(decodedIv);
      } else {
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);

        this.iv = new IvParameterSpec(initializationVector);
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getSecretKey() {
    return this.secretKeyField;
  }

  @Override
  public String getIv() {
    return this.ivField;
  }

  public ProjectStorageCredentialsJPA getCredentials() {
    return credentials;
  }

}
