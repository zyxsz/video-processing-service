package com.zyx.vps.application.services;

import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import com.zyx.vps.application.entities.Project;
import com.zyx.vps.application.entities.Upload;
import com.zyx.vps.application.entities.local.ProjectLocal;
import com.zyx.vps.application.enums.StorageProviders;
import com.zyx.vps.application.enums.UploadStatus;
import com.zyx.vps.application.repositories.UploadsRepository;
import com.zyx.vps.application.storages.StorageProvider;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.TemplateAcknowledgementMode;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

public class UploadsService {

  private String uploadQueue = "vps-upload-queue";

  private SqsAsyncClient sqsAsyncClient;
  private SqsTemplate sqsTemplate;
  private UploadsRepository uploadsRepository;
  private StorageProvider storageProvider;

  public UploadsService(UploadsRepository uploadsRepository, StorageProvider storageProvider,
      SqsAsyncClient sqsAsyncClient) {
    this.uploadsRepository = uploadsRepository;
    this.storageProvider = storageProvider;
    this.sqsAsyncClient = sqsAsyncClient;
    this.sqsTemplate = SqsTemplate.builder()
        .sqsAsyncClient(sqsAsyncClient)
        .configure(options -> options
            .acknowledgementMode(TemplateAcknowledgementMode.MANUAL)
            .defaultQueue(this.uploadQueue))
        .build();

  }

  public List<Upload> findUploadsByProjectId(String projectId) {
    List<Upload> uploads = this.uploadsRepository.findByProjectId(projectId);

    return uploads;
  }

  public Upload create(String projectId) {
    String key = UUID.randomUUID().toString();

    Project project = new ProjectLocal(projectId, null, null);

    String presignedUrl = this.storageProvider.generateUploadSignedURL(key, "video/mp4");

    Upload upload = new Upload(null, presignedUrl, key, null, null, null, StorageProviders.S3,
        UploadStatus.WAITING_UPLOAD, project);

    this.uploadsRepository.create(upload);

    return upload;
  }

  public void reciveUpload(String key, Long size) {
    Upload upload = this.uploadsRepository.findByKey(key);

    if (upload == null) {
      System.out.println("upload not found");
      return;
    }

    upload.setStatus(UploadStatus.WAITING_UPLOAD);
    upload.setSize(size);

    this.uploadsRepository.save(upload);

    JSONObject payload = new JSONObject();

    payload.put("uploadId", upload.getId());
    payload.put("key", key);
    payload.put("type", "upload-processing");

    this.sqsTemplate.send(to -> to.queue(this.uploadQueue).payload(payload.toString()));
  }
}
