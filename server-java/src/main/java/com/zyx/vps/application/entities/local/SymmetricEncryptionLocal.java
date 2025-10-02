package com.zyx.vps.application.entities.local;

import com.zyx.vps.application.entities.ProjectStorageCredentials;
import com.zyx.vps.application.entities.SymmetricEncryption;
import com.zyx.vps.infra.database.jpa.entities.ProjectStorageCredentialsJPA;

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

public class SymmetricEncryptionLocal implements SymmetricEncryption {

  private String id;

  private String algorithm = "AES";

  private KeyGenerator keygenerator;

  private SecretKey key;
  private IvParameterSpec iv;

  private byte[] ivVector;

  public SymmetricEncryptionLocal(String secretKey, String iv) {
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
      } else {
        byte[] initializationVector = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initializationVector);

        this.iv = new IvParameterSpec(initializationVector);
        this.ivVector = initializationVector;
      }
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

  public String getSecretKey() {
    return Base64.getEncoder().encodeToString(this.key.getEncoded());
  }

  public String getIv() {
    return Base64.getEncoder().encodeToString(this.ivVector);
  }

  @Override
  public ProjectStorageCredentials getCredentials() {
    // TODO Auto-generated method stub
    return null;
  }
}
