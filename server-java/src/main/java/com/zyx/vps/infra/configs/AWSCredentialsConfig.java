package com.zyx.vps.infra.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;

@Configuration
public class AWSCredentialsConfig {

  @Bean
  public AwsCredentialsProvider customAwsCredentialsProvider() {
    StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider
        .create(AwsBasicCredentials.create("01", "02"));

    return credentialsProvider;
  }

}
