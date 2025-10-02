package com.zyx.vps.infra.http.configs;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zyx.vps.infra.configs.ApplicationConfig;

@Configuration
public class DataSourceConfig {

  @Bean
  @Primary
  public DataSource getDataSource() throws IOException {
    ApplicationConfig applicationConfig = new ApplicationConfig().initialize();

    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

    dataSourceBuilder.driverClassName(applicationConfig.getDatabase().getDriverClassName());
    dataSourceBuilder.url(
        applicationConfig.getDatabase().getUrl());

    dataSourceBuilder.username(applicationConfig.getDatabase().getUsername());
    dataSourceBuilder.password(applicationConfig.getDatabase().getPassword());

    return dataSourceBuilder.build();
  }

}