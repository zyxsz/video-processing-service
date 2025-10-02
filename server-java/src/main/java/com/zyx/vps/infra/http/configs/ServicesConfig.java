package com.zyx.vps.infra.http.configs;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.zyx.vps.application.repositories.ApiTokenRepository;
import com.zyx.vps.application.repositories.ProjectStorageActivitiesRepository;
import com.zyx.vps.application.repositories.ProjectStoragesRepository;
import com.zyx.vps.application.repositories.ProjectsRepository;
import com.zyx.vps.application.repositories.UploadsRepository;
import com.zyx.vps.application.repositories.UsersRepository;
import com.zyx.vps.application.repositories.VideosRepository;
import com.zyx.vps.application.services.AuthService;
import com.zyx.vps.application.services.GoogleOAuthService;
import com.zyx.vps.application.services.ProjectStoragesService;
import com.zyx.vps.application.services.ProjectsService;
import com.zyx.vps.application.services.UploadsService;
import com.zyx.vps.application.services.VideosService;
import com.zyx.vps.application.storages.StorageProvider;
import com.zyx.vps.infra.storages.S3StorageProvider;

import io.awspring.cloud.sqs.listener.MessageListenerContainer;
import io.awspring.cloud.sqs.listener.SqsMessageListenerContainer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class ServicesConfig {
  // SdkHttpClient urlHttpClient = UrlConnectionHttpClient.create();

  @Bean
  public AuthService authService(GoogleOAuthService gooogleOAuthService, UsersRepository usersRepository,
      ApiTokenRepository apiTokenRepository) {
    AuthService authService = new AuthService(gooogleOAuthService, usersRepository, apiTokenRepository);

    return authService;
  }

  @Bean
  public ProjectsService projectsService(ProjectsRepository projectsRepository) {
    ProjectsService projectsService = new ProjectsService(projectsRepository);

    return projectsService;
  }

  @Bean
  public UploadsService uploadsService(UploadsRepository uploadsRepository, StorageProvider storageProvider,
      SqsAsyncClient sqsAsyncClient) {
    UploadsService uploadsService = new UploadsService(uploadsRepository, storageProvider, sqsAsyncClient);

    return uploadsService;
  }

  @Bean
  public VideosService videosService(VideosRepository videosRepository) {
    VideosService videosService = new VideosService(videosRepository);

    return videosService;
  }

  @Bean
  public StorageProvider storageProvider() {
    StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider
        .create(AwsBasicCredentials.create("01", "02"));

    Region region = Region.US_EAST_1;
    S3Client s3 = S3Client.builder()
        .credentialsProvider(credentialsProvider)
        .region(region)
        .build();

    StorageProvider storageProvider = new S3StorageProvider(s3, region, credentialsProvider);

    return storageProvider;
  }

  @Bean
  public SqsAsyncClient sqsAsyncClient() {
    StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider
        .create(AwsBasicCredentials.create("01", "02"));
    Region region = Region.US_EAST_1;

    SqsAsyncClient sqsAsyncClient = SqsAsyncClient.builder().credentialsProvider(credentialsProvider)
        .region(region).build();

    return sqsAsyncClient;
  }

  @Bean
  public S3AsyncClient s3AsyncClient() {
    StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider
        .create(AwsBasicCredentials.create("01", "02"));
    Region region = Region.US_EAST_1;

    S3AsyncClient s3AsyncClient = S3AsyncClient.crtBuilder().credentialsProvider(credentialsProvider).region(region)
        .build();

    return s3AsyncClient;
  }

  @Bean
  public ProjectStoragesService projectStoragesService(ProjectStoragesRepository projectStoragesRepository,
      ProjectsRepository projectsRepository, ProjectStorageActivitiesRepository projectStorageActivitiesRepository) {
    ProjectStoragesService projectStoragesService = new ProjectStoragesService(projectStoragesRepository,
        projectsRepository, projectStorageActivitiesRepository);

    return projectStoragesService;
  }

}
