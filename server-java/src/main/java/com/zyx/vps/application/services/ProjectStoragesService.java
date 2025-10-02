package com.zyx.vps.application.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONObject;

import com.zyx.vps.application.dto.projects.CreateProjectStorageDTO;
import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.ProjectStorage;
import com.zyx.vps.application.entities.ProjectStorageActivity;
import com.zyx.vps.application.entities.ProjectStorageCredentials;
import com.zyx.vps.application.entities.SymmetricEncryption;
import com.zyx.vps.application.entities.local.ProjectStorageActivityLocal;
import com.zyx.vps.application.entities.local.ProjectStorageCredentialsLocal;
import com.zyx.vps.application.entities.local.ProjectStorageLocal;
import com.zyx.vps.application.entities.local.SymmetricEncryptionLocal;
import com.zyx.vps.application.enums.ProjectStorageActivityOperationType;
import com.zyx.vps.application.enums.ProjectStoreStatus;
import com.zyx.vps.application.errors.NotfoundException;
import com.zyx.vps.application.repositories.ProjectStorageActivitiesRepository;
import com.zyx.vps.application.repositories.ProjectStoragesRepository;
import com.zyx.vps.application.repositories.ProjectsRepository;
import com.zyx.vps.infra.database.jpa.mappers.ProjectStorageActivityMapper;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityRequest;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;
import software.amazon.awssdk.services.sts.model.StsException;

public class ProjectStoragesService {
  private ProjectStoragesRepository projectStoragesRepository;
  private ProjectsRepository projectsRepository;
  private ProjectStorageActivitiesRepository projectStorageActivitiesRepository;

  public ProjectStoragesService(ProjectStoragesRepository projectStoragesRepository,
      ProjectsRepository projectsRepository, ProjectStorageActivitiesRepository projectStorageActivitiesRepository) {
    this.projectStoragesRepository = projectStoragesRepository;
    this.projectsRepository = projectsRepository;
    this.projectStorageActivitiesRepository = projectStorageActivitiesRepository;
  }

  public ProjectStorage findStorageById(String storageId, String projectId) {
    ProjectStorage projectStorage = this.projectStoragesRepository.findProjectStorageById(storageId, projectId);

    return projectStorage;
  }

  public List<ProjectStorage> findProjectStoragesByProjectId(String projectId) {
    List<ProjectStorage> projectStorages = this.projectStoragesRepository.findAllByProjectId(projectId);

    return projectStorages;
  }

  public List<ProjectStorageActivity> findActivities(String userId, String projectId, String storageId) {
    this.projectsRepository.validateProjectByUserId(projectId, userId);

    ProjectStorage projectStorage = this.findStorageById(storageId, projectId);

    if (projectStorage == null)
      throw new NotfoundException("Project not found");

    List<ProjectStorageActivity> activities = this.projectStorageActivitiesRepository.findAllFromStorageId(storageId);

    return activities;
  }

  public ProjectStorage create(String projectId, CreateProjectStorageDTO dto, String userId) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

    Date now = Date.from(Instant.now());

    ProjectStorage checkIfAlreadyExists = this.projectStoragesRepository.findByProjectIdAndBucketName(projectId,
        dto.getBucketName());

    if (checkIfAlreadyExists != null)
      throw new Error("Project storage already exists");

    Project project = this.projectsRepository.findByIdAndUserId(projectId, userId);

    if (project == null)
      throw new Error("Project not found");

    CheckProviderCredentials checkCredentialsResponse = this.checkProviderCredentials(dto);

    if (checkCredentialsResponse == null)
      throw new Error("Invalid credentials for provider");

    Boolean isBucketValid = this.checkProviderBucket(dto);

    if (!isBucketValid)
      throw new Error("Invalid bucket for provider");

    SymmetricEncryption symmetricEncryption = new SymmetricEncryptionLocal(null, null);
    ProjectStorageCredentials projectStorageCredentials = new ProjectStorageCredentialsLocal(null, symmetricEncryption);

    projectStorageCredentials.setAccessKeyId(dto.getAccessKeyId());
    projectStorageCredentials.setSecretAccessKey(dto.getSecretAccessKey());

    ProjectStorage projectStorage = new ProjectStorageLocal(null);

    projectStorage.setProject(project);
    projectStorage.setRegion(dto.getRegion());
    projectStorage.setBucketName(dto.getBucketName());
    projectStorage.setCredentials(projectStorageCredentials);
    projectStorage.setStatus(ProjectStoreStatus.VALID);

    JSONObject activityCredentialsFetchData = new JSONObject();

    activityCredentialsFetchData.put("requestedAt", sdf.format(now));

    ProjectStorageActivity activityCredentialsFetch = new ProjectStorageActivityLocal(null);

    activityCredentialsFetch.setOperationType(ProjectStorageActivityOperationType.CHECK_CREDENTIALS_FETCH);
    activityCredentialsFetch.setOperationData(activityCredentialsFetchData.toString());
    activityCredentialsFetch.setExecutedAt(now);
    activityCredentialsFetch.setStorage(projectStorage);

    JSONObject activityCredentialsResultData = new JSONObject();
    JSONObject credentialsResultData = new JSONObject();

    credentialsResultData.put("arn", checkCredentialsResponse.getArn());
    credentialsResultData.put("account", checkCredentialsResponse.getAccount());
    credentialsResultData.put("userId", checkCredentialsResponse.getUserId());
    credentialsResultData.put("isValid", checkCredentialsResponse.isValid());

    activityCredentialsResultData.put("requestedAt", sdf.format(now));
    activityCredentialsResultData.put("finishedAt", sdf.format(Date.from(Instant.now())));
    activityCredentialsResultData.put("time", Date.from(Instant.now()).getTime() - now.getTime());
    activityCredentialsResultData.put("data", credentialsResultData);

    ProjectStorageActivity activityCredentialsResult = new ProjectStorageActivityLocal(null);

    activityCredentialsResult.setOperationType(ProjectStorageActivityOperationType.CHECK_CREDENTIALS_RESULT);
    activityCredentialsResult.setOperationData(activityCredentialsResultData.toString());
    activityCredentialsResult.setExecutedAt(Date.from(Instant.now()));
    activityCredentialsResult.setStorage(projectStorage);

    this.projectStoragesRepository.create(projectStorage);
    this.projectStorageActivitiesRepository.saveAll(Arrays.asList(activityCredentialsFetch,
        activityCredentialsResult));
    // projectStorage.setActivities(activities);

    return projectStorage;
  }

  private CheckProviderCredentials checkProviderCredentials(CreateProjectStorageDTO dto) {
    switch (dto.getProvider()) {
      case S3:
        return this.checkS3Credentials(dto.getRegion(), dto.getAccessKeyId(), dto.getSecretAccessKey());
    }

    return null;
  }

  private boolean checkProviderBucket(CreateProjectStorageDTO dto) {
    switch (dto.getProvider()) {
      case S3:
        return this.checkS3Bucket(dto.getRegion(), dto.getAccessKeyId(), dto.getSecretAccessKey(), dto.getBucketName());
    }

    return false;
  }

  private CheckProviderCredentials checkS3Credentials(String region, String accessKeyId, String secretAccessKey) {
    Region awsRegion = Region.of(region);
    StsClient stsClient = StsClient.builder().region(awsRegion).build();

    StaticCredentialsProvider credentials = StaticCredentialsProvider
        .create(AwsBasicCredentials.create(accessKeyId, secretAccessKey));
    AwsRequestOverrideConfiguration config = AwsRequestOverrideConfiguration.builder().credentialsProvider(credentials)
        .build();

    try {
      GetCallerIdentityResponse response = stsClient
          .getCallerIdentity(GetCallerIdentityRequest.builder().overrideConfiguration(config).build());

      if (response.userId() == null)
        return null;

      CheckProviderCredentials checkProviderCredentials = new CheckProviderCredentials();

      checkProviderCredentials.setValid(true);
      checkProviderCredentials.setAccount(response.account());
      checkProviderCredentials.setArn(response.arn());
      checkProviderCredentials.setUserId(response.userId());

      return checkProviderCredentials;
    } catch (StsException e) {
      return null;
    }

  }

  private boolean checkS3Bucket(String region, String accessKeyId, String secretAccessKey, String bucketName) {
    Region awsRegion = Region.of(region);

    StaticCredentialsProvider credentials = StaticCredentialsProvider
        .create(AwsBasicCredentials.create(accessKeyId, secretAccessKey));

    S3Client s3Client = S3Client.builder().credentialsProvider(credentials).region(awsRegion).build();

    try {
      GetBucketVersioningResponse response = s3Client
          .getBucketVersioning(GetBucketVersioningRequest.builder().bucket(bucketName).build());

      if (response.sdkHttpResponse().statusCode() != 200)
        return false;

      return true;
    } catch (NoSuchBucketException e) {
      e.printStackTrace();

      return false;
    }
  }

}

class CheckProviderCredentials {
  private String arn;
  private String account;
  private String userId;
  private boolean valid;

  public void setAccount(String account) {
    this.account = account;
  }

  public void setArn(String arn) {
    this.arn = arn;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  public String getAccount() {
    return account;
  }

  public String getArn() {
    return arn;
  }

  public String getUserId() {
    return userId;
  }

  public boolean isValid() {
    return valid;
  }

}