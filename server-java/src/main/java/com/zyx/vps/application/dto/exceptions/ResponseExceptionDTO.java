package com.zyx.vps.application.dto.exceptions;

import java.util.Map;

public class ResponseExceptionDTO {
  private int status;
  private String message;
  private Map<String, String> fields;

  public Map<String, String> getFields() {
    return fields;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

  public void setFields(Map<String, String> fields) {
    this.fields = fields;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
