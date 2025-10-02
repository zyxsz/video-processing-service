package com.zyx.vps.application.repositories;

import java.util.List;

import com.zyx.vps.application.entities.SymmetricEncryption;

public interface SymmetricEncryptionRepository {
  public void create(SymmetricEncryption symmetricEncryption);

  public List<SymmetricEncryption> findAll();
}
