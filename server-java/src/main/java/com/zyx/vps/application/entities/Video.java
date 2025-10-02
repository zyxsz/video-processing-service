package com.zyx.vps.application.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Video {

  private String id;

  private Upload upload;
  private Project project;

  private double duration;
  private long size;
  private long bitRate;
  private String formatName;
  private double startTime;
  private int streamsCount;

  private List<Stream> streams;

  private Date updatedAt;
  private Date createdAt;

  public Video(String id) {
    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());

    if (id != null) {
      this.id = id;
    } else {
      this.id = UUID.randomUUID().toString();
    }
  }

  public String getId() {
    return id;
  }

  public long getBitRate() {
    return bitRate;
  }

  public double getDuration() {
    return duration;
  }

  public String getFormatName() {
    return formatName;
  }

  public long getSize() {
    return size;
  }

  public double getStartTime() {
    return startTime;
  }

  public Upload getUpload() {
    return upload;
  }

  public Project getProject() {
    return project;
  }

  public int getStreamsCount() {
    return streamsCount;
  }

  public List<Stream> getStreams() {
    return streams;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setStreams(List<Stream> streams) {
    this.streams = streams;
  }

  public void setStreamsCount(int streamsCount) {
    this.streamsCount = streamsCount;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setBitRate(long bitRate) {
    this.bitRate = bitRate;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setDuration(double duration) {
    this.duration = duration;
  }

  public void setFormatName(String formatName) {
    this.formatName = formatName;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public void setStartTime(double startTime) {
    this.startTime = startTime;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setUpload(Upload upload) {
    this.upload = upload;
  }

}
