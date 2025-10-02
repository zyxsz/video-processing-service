package com.zyx.vps.infra.storages.consumers;

import io.awspring.cloud.sqs.annotation.SqsListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zyx.vps.application.storages.consumers.UploadConsumer;

@Component
public class S3UploadConsumer {

  @Autowired
  private UploadConsumer uploadConsumer;

  @SqsListener(value = "vps", maxConcurrentMessages = "1", maxMessagesPerPoll = "1")
  public void listen(String message) {
    this.uploadConsumer.listen(message);
  }

}
