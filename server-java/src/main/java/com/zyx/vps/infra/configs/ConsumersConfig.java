package com.zyx.vps.infra.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyx.vps.application.repositories.UploadsRepository;
import com.zyx.vps.application.repositories.VideosRepository;
import com.zyx.vps.application.services.UploadsService;
import com.zyx.vps.application.services.VideoProcessor;
import com.zyx.vps.application.storages.consumers.UploadConsumer;
import com.zyx.vps.application.storages.consumers.UploadProcessorConsumer;

import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration
public class ConsumersConfig {

  @Bean
  public UploadConsumer uploadConsumer(UploadsService uploadsService) {
    UploadConsumer uploadConsumer = new UploadConsumer(uploadsService);

    return uploadConsumer;
  }

  @Bean
  public UploadProcessorConsumer uploadProcessorConsumer(UploadsService uploadsService,
      UploadsRepository uploadsRepository, S3AsyncClient s3AsyncClient, VideoProcessor videoProcessor,
      VideosRepository videosRepository) {
    UploadProcessorConsumer uploadProcessorConsumer = new UploadProcessorConsumer(uploadsService, uploadsRepository,
        s3AsyncClient, videoProcessor, videosRepository);

    return uploadProcessorConsumer;
  }
}
