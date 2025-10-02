package com.zyx.vps.application.storages.consumers;

import java.util.List;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.zyx.vps.application.services.UploadsService;

public class UploadConsumer {

  private UploadsService uploadsService;

  public UploadConsumer(UploadsService uploadsService) {
    this.uploadsService = uploadsService;
  }

  public void listen(String message) {
    S3EventNotification s3EventNotification = S3EventNotification.parseJson(message);

    List<S3EventNotificationRecord> records = s3EventNotification.getRecords();

    for (S3EventNotificationRecord record : records) {

      if (record.getEventName().equals("ObjectCreated:Put")) {
        String objectKey = record.getS3().getObject().getKey();
        Long objectSize = record.getS3().getObject().getSizeAsLong();

        System.out.println(objectKey);
        this.uploadsService.reciveUpload(objectKey, objectSize);
      }
    }
  }

}
