package com.zyx.vps.infra.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyx.vps.application.services.VideoProcessor;

@Configuration
public class ProcessorsConfig {
  @Bean
  public VideoProcessor videoProcessor() {
    VideoProcessor videoProcessor = new VideoProcessor();

    return videoProcessor;
  }
}
