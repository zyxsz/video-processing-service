package com.zyx.vps.application.dto.consumers;

import org.json.JSONObject;

public class UploadProcessorDTO {

  private String uploadId;
  private String type;
  private String key;

  public String getKey() {
    return key;
  }

  public String getType() {
    return type;
  }

  public String getUploadId() {
    return uploadId;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setUploadId(String uploadId) {
    this.uploadId = uploadId;
  }

  public static UploadProcessorDTO fromString(String payloadString) {
    JSONObject payload = new JSONObject(payloadString);

    UploadProcessorDTO uploadProcessorDTO = new UploadProcessorDTO();

    uploadProcessorDTO.setKey(payload.getString("key"));
    uploadProcessorDTO.setUploadId(payload.getString("uploadId"));
    uploadProcessorDTO.setType(payload.getString("type"));

    return uploadProcessorDTO;
  }

}
