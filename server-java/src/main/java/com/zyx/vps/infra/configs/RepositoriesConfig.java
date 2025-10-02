package com.zyx.vps.infra.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyx.vps.application.entities.SymmetricEncryption;
import com.zyx.vps.application.repositories.ApiTokenRepository;
import com.zyx.vps.application.repositories.ProjectStorageActivitiesRepository;
import com.zyx.vps.application.repositories.ProjectStoragesRepository;
import com.zyx.vps.application.repositories.ProjectsRepository;
import com.zyx.vps.application.repositories.SymmetricEncryptionRepository;
import com.zyx.vps.application.repositories.UploadsRepository;
import com.zyx.vps.application.repositories.UsersRepository;
import com.zyx.vps.application.repositories.VideosRepository;
import com.zyx.vps.infra.database.jpa.repositories.ApiTokenJPARepository;
import com.zyx.vps.infra.database.jpa.repositories.ProjectStorageActivitiesJPARepository;
import com.zyx.vps.infra.database.jpa.repositories.ProjectsJPARepository;
import com.zyx.vps.infra.database.jpa.repositories.ProjectsStoragesJPARepository;
import com.zyx.vps.infra.database.jpa.repositories.SymmetricEncryptionJPARepository;
import com.zyx.vps.infra.database.jpa.repositories.UsersJPARepository;
import com.zyx.vps.infra.database.memory.repositories.ProjectStoragesLocalRepository;
import com.zyx.vps.infra.database.memory.repositories.UploadsLocalRepository;
import com.zyx.vps.infra.database.memory.repositories.VideosLocalRepository;

@Configuration
public class RepositoriesConfig {
   @Bean
   public UsersRepository usersRepository() {
      UsersRepository userReposistory = new UsersJPARepository();

      return userReposistory;
   }

   @Bean
   public ApiTokenRepository apiTokenRepository() {
      ApiTokenRepository apiTokenRepository = new ApiTokenJPARepository();

      return apiTokenRepository;
   }

   @Bean
   public ProjectsRepository projectsRepository() {
      ProjectsRepository projectsRepository = new ProjectsJPARepository();

      return projectsRepository;
   }

   @Bean
   public UploadsRepository uploadsRepository() {
      UploadsRepository uploadsRepository = new UploadsLocalRepository();

      return uploadsRepository;
   }

   @Bean
   public VideosRepository videosRepository() {
      VideosRepository videosRepository = new VideosLocalRepository();

      return videosRepository;
   }

   @Bean
   public ProjectStoragesRepository projectStoragesRepository() {
      ProjectStoragesRepository projectStoragesRepository = new ProjectsStoragesJPARepository();

      return projectStoragesRepository;
   }

   @Bean
   public SymmetricEncryptionRepository symmetricEncryptionRepository() {
      SymmetricEncryptionRepository symmetricEncryptionRepository = new SymmetricEncryptionJPARepository();

      return symmetricEncryptionRepository;
   }

   @Bean
   public ProjectStorageActivitiesRepository projectStorageActivitiesRepository() {
      ProjectStorageActivitiesRepository projectStorageActivitiesRepository = new ProjectStorageActivitiesJPARepository();

      return projectStorageActivitiesRepository;
   }

}
