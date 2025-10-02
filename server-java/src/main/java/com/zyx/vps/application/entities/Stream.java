package com.zyx.vps.application.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.math.Fraction;

import com.zyx.vps.application.enums.CodecType;

public class Stream {
  private String id;

  private int index;

  private long bitRate;
  private long frames;
  private Fraction averageFrameRate;
  private int sampleRate;

  private CodecType codecType;
  private String codecLongName;
  private String codecName;
  private String codecTag;

  private String aspectRatio;

  private double duration;
  private long durationTs;

  private int width;
  private int height;

  private String pixFmt;
  private String profile;

  private int channels;
  private String channelLayout;

  private Locale lang;

  private Map<String, String> tags;

  private Date updatedAt;
  private Date createdAt;

  public Stream(String id) {
    this.updatedAt = Timestamp.from(Instant.now());
    this.createdAt = Timestamp.from(Instant.now());

    if (id != null) {
      this.id = id;
    } else {
      this.id = UUID.randomUUID().toString();
    }
  }

  public String getAspectRatio() {
    return aspectRatio;
  }

  public Fraction getAverageFrameRate() {
    return averageFrameRate;
  }

  public long getBitRate() {
    return bitRate;
  }

  public String getChannelLayout() {
    return channelLayout;
  }

  public int getChannels() {
    return channels;
  }

  public String getCodecLongName() {
    return codecLongName;
  }

  public String getCodecName() {
    return codecName;
  }

  public String getCodecTag() {
    return codecTag;
  }

  public CodecType getCodecType() {
    return codecType;
  }

  public double getDuration() {
    return duration;
  }

  public long getDurationTs() {
    return durationTs;
  }

  public long getFrames() {
    return frames;
  }

  public int getHeight() {
    return height;
  }

  public String getId() {
    return id;
  }

  public int getIndex() {
    return index;
  }

  public Locale getLang() {
    return lang;
  }

  public String getPixFmt() {
    return pixFmt;
  }

  public String getProfile() {
    return profile;
  }

  public int getSampleRate() {
    return sampleRate;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public int getWidth() {
    return width;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setAspectRatio(String aspectRatio) {
    this.aspectRatio = aspectRatio;
  }

  public void setAverageFrameRate(Fraction averageFrameRate) {
    this.averageFrameRate = averageFrameRate;
  }

  public void setBitRate(long bitRate) {
    this.bitRate = bitRate;
  }

  public void setChannelLayout(String channelLayout) {
    this.channelLayout = channelLayout;
  }

  public void setChannels(int channels) {
    this.channels = channels;
  }

  public void setCodecLongName(String codecLongName) {
    this.codecLongName = codecLongName;
  }

  public void setCodecName(String codecName) {
    this.codecName = codecName;
  }

  public void setCodecTag(String codecTag) {
    this.codecTag = codecTag;
  }

  public void setCodecType(CodecType codecType) {
    this.codecType = codecType;
  }

  public void setDuration(double duration) {
    this.duration = duration;
  }

  public void setDurationTs(long durationTs) {
    this.durationTs = durationTs;
  }

  public void setFrames(long frames) {
    this.frames = frames;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setLang(Locale lang) {
    this.lang = lang;
  }

  public void setPixFmt(String pixFmt) {
    this.pixFmt = pixFmt;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }

  public void setSampleRate(int sampleRate) {
    this.sampleRate = sampleRate;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public void setWidth(int width) {
    this.width = width;
  }
}
