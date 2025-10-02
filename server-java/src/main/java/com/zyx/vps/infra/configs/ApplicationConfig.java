package com.zyx.vps.infra.configs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.zyx.vps.application.enums.Environment;

public class ApplicationConfig {

  @Autowired
  private ApplicationContext appContext;

  private Environment env;
  private DatabaseConfig database;
  private AWSConfig aws;

  public ApplicationConfig() {
  }

  public AWSConfig getAws() {
    return aws;
  }

  public DatabaseConfig getDatabase() {
    return database;
  }

  public Environment getEnv() {
    return env;
  }

  @SuppressWarnings("rawtypes")
  public ApplicationConfig initialize() throws IOException {
    DumperOptions options = new DumperOptions();
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    options.setIndent(2);
    options.setPrettyFlow(true);

    Yaml yaml = new Yaml(options);

    File configFile = new File("config.yaml");

    if (!configFile.exists()) {
      Map<String, Object> data = new HashMap<String, Object>();
      Map<String, String> database = new HashMap<String, String>();

      database.put("driverClassName", "");
      database.put("url", "");
      database.put("username", "");
      database.put("password", "");

      data.put("env", "DEV");
      data.put("database", database);

      BufferedWriter writer = new BufferedWriter(new FileWriter(configFile.getPath(), true));
      yaml.dump(data, writer);

      SpringApplication.exit(this.appContext, (ExitCodeGenerator) () -> 0);
    }

    try {

      InputStream configFileInputStream = new FileInputStream(configFile);

      Map applicationConfig = yaml.load(configFileInputStream);

      this.env = Environment.valueOf((String) applicationConfig.get("env"));

      if (applicationConfig.get("database") != null) {
        DatabaseConfig database = new DatabaseConfig();

        database.setUsername((String) ((Map) applicationConfig.get("database")).get("username"));
        database.setUrl((String) ((Map) applicationConfig.get("database")).get("url"));
        database.setDriverClassName((String) ((Map) applicationConfig.get("database")).get("driverClassName"));
        database.setPassword((String) ((Map) applicationConfig.get("database")).get("password"));

        this.database = database;

      }

      if (applicationConfig.get("aws") != null) {
        AWSConfig aws = new AWSConfig();

        aws.setAccessKeyId((String) ((Map) applicationConfig.get("aws")).get("accessKeyId"));
        aws.setSecretAccessKey((String) ((Map) applicationConfig.get("aws")).get("secretAccessKey"));
        aws.setSessionToken((String) ((Map) applicationConfig.get("aws")).get("sessionToken"));

        this.aws = aws;

      }
      return this;
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return this;
  }

}
