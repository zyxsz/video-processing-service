package com.zyx.vps.infra.storages;

import java.time.Duration;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.BucketNotificationConfiguration;
import com.amazonaws.services.s3.model.QueueConfiguration;
import com.amazonaws.services.s3.model.S3Event;
import com.amazonaws.services.s3.model.SetBucketNotificationConfigurationRequest;
import com.zyx.vps.application.storages.StorageProvider;

import io.awspring.cloud.sqs.annotation.SqsListener;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

public class S3StorageProvider implements StorageProvider {

  // arn:aws:sns:us-east-1:624049592578:vps-video-upload-topic
  private String SQSQueueArn = "arn:aws:sqs:us-east-1:624049592578:vps";
  private String queueName = "vps";
  private String bucketName = "vps-server-storage";
  private S3Client s3Client;

  private Region region;
  private AwsCredentialsProvider credentialsProvider;

  public S3StorageProvider(S3Client s3Client, Region region, AwsCredentialsProvider credentialsProvider) {
    this.s3Client = s3Client;
    this.region = region;
    this.credentialsProvider = credentialsProvider;
  }

  @Override
  public String generateUploadSignedURL(String key, String contentType) {
    try (S3Presigner presigner = S3Presigner
        .builder()
        .credentialsProvider(this.credentialsProvider)
        .region(this.region)
        .build()) {

      Map<String, String> metadata = new HashMap<String, String>();

      metadata.put("Content-Type", contentType);

      PutObjectRequest objectRequest = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(key)
          // .metadata(metadata)
          .build();
      PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
          .signatureDuration(Duration.ofMinutes(10)) // The URL expires in 10 minutes.
          .putObjectRequest(objectRequest)
          .build();

      PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
      String presignedUrl = presignedRequest.url().toString();

      return presignedUrl;
    }
  }

}
