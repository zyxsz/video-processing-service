package com.zyx.vps.infra.storages.consumers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zyx.vps.application.dto.consumers.UploadProcessorDTO;
import com.zyx.vps.application.storages.consumers.UploadProcessorConsumer;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class SQSUploadProcessorConsumer {
  // private String queueName = "vps-upload-queue";

  @Autowired
  private UploadProcessorConsumer uploadProcessorConsumer;

  @SqsListener(value = "vps-upload-queue", maxConcurrentMessages = "1", maxMessagesPerPoll = "1")
  public void listen(String message) throws IOException {
    UploadProcessorDTO uploadProcessorDTO = UploadProcessorDTO.fromString(message);

    this.uploadProcessorConsumer.listen(uploadProcessorDTO);

  }
}
