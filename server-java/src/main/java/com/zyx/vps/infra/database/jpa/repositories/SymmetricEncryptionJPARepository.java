package com.zyx.vps.infra.database.jpa.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zyx.vps.application.entities.SymmetricEncryption;
import com.zyx.vps.application.repositories.SymmetricEncryptionRepository;
import com.zyx.vps.infra.database.jpa.entities.SymmetricEncryptionJPA;
import com.zyx.vps.infra.database.jpa.mappers.SymmetricEncryptionMapper;

interface JPASymmetricEncryptionRepository extends JpaRepository<SymmetricEncryptionJPA, String> {
  SymmetricEncryption save(SymmetricEncryption symmetricEncryption);

  List<SymmetricEncryptionJPA> findAll();
}

public class SymmetricEncryptionJPARepository implements SymmetricEncryptionRepository {

  @Autowired
  private JPASymmetricEncryptionRepository repository;

  @Override
  public void create(SymmetricEncryption symmetricEncryption) {
    SymmetricEncryptionJPA symmetricEncryptionJPA = SymmetricEncryptionMapper.toJPA(symmetricEncryption);

    this.repository.save(symmetricEncryptionJPA);
  }

  @Override
  public List<SymmetricEncryption> findAll() {
    List<SymmetricEncryptionJPA> symmetricEncryptions = this.repository.findAll();

    return symmetricEncryptions.stream().map(s -> (SymmetricEncryption) s).toList();
  }

}
