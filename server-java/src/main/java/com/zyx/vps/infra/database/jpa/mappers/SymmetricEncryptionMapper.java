package com.zyx.vps.infra.database.jpa.mappers;

import com.zyx.vps.application.entities.SymmetricEncryption;
import com.zyx.vps.infra.database.jpa.entities.SymmetricEncryptionJPA;

public class SymmetricEncryptionMapper {

  public static SymmetricEncryptionJPA toJPA(SymmetricEncryption symmetricEncryption) {
    if (symmetricEncryption instanceof SymmetricEncryptionJPA)
      return (SymmetricEncryptionJPA) symmetricEncryption;

    SymmetricEncryptionJPA symmetricEncryptionJPA = new SymmetricEncryptionJPA(symmetricEncryption.getSecretKey(),
        symmetricEncryption.getIv());

    symmetricEncryptionJPA.setId(symmetricEncryption.getId());

    if (symmetricEncryption.getCredentials() != null) {
      symmetricEncryptionJPA
          .setCredentials(ProjectStorageCredentialsMapper.toJPA(symmetricEncryption.getCredentials()));
    }

    return symmetricEncryptionJPA;
  }
}
