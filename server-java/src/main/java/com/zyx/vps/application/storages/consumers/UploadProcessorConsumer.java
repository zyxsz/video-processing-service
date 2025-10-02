package com.zyx.vps.application.storages.consumers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Locale.IsoCountryCode;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.zyx.vps.application.dto.consumers.UploadProcessorDTO;
import com.zyx.vps.application.entities.Stream;
import com.zyx.vps.application.entities.Upload;
import com.zyx.vps.application.entities.Video;
import com.zyx.vps.application.enums.CodecType;
import com.zyx.vps.application.repositories.UploadsRepository;
import com.zyx.vps.application.repositories.VideosRepository;
import com.zyx.vps.application.services.UploadsService;
import com.zyx.vps.application.services.VideoProcessor;

import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.CompletedFileDownload;
import software.amazon.awssdk.transfer.s3.model.DownloadFileRequest;
import software.amazon.awssdk.transfer.s3.model.FileDownload;

public class UploadProcessorConsumer {

  private String bucketName = "vps-server-storage";
  private String tmpFolder = new File("./tmp").getAbsolutePath();

  private UploadsRepository uploadsRepository;
  private UploadsService uploadsService;
  private S3AsyncClient s3AsyncClient;
  private VideoProcessor videoProcessor;
  private VideosRepository videosRepository;

  public UploadProcessorConsumer(UploadsService uploadsService, UploadsRepository uploadsRepository,
      S3AsyncClient s3AsyncClient, VideoProcessor videoProcessor, VideosRepository videosRepository) {
    this.uploadsService = uploadsService;
    this.uploadsRepository = uploadsRepository;
    this.s3AsyncClient = s3AsyncClient;
    this.videoProcessor = videoProcessor;
    this.videosRepository = videosRepository;
  }

  public void listen(UploadProcessorDTO uploadProcessorDTO) throws IOException {
    Upload upload = this.uploadsRepository.findById(uploadProcessorDTO.getUploadId());

    if (upload == null) {
      System.out.println("UploadProcessorConsumer: upload not found");

      return;
    }

    System.out.println("Download started...");

    Path downloadPath = Path.of(tmpFolder + "/" + upload.getKey());
    System.out.println(downloadPath.toAbsolutePath().toString());

    S3TransferManager transferManager = S3TransferManager.builder().s3Client(this.s3AsyncClient).build();

    DownloadFileRequest downloadFileRequest = DownloadFileRequest.builder()
        .getObjectRequest(b -> b.bucket(bucketName).key(upload.getKey()))
        .destination(downloadPath)
        .build();

    FileDownload downloadFile = transferManager.downloadFile(downloadFileRequest);

    CompletedFileDownload downloadResult = downloadFile.completionFuture().join();

    transferManager.close();

    System.out.println("Download finished: " + downloadResult.response().contentLength());

    FFmpegProbeResult result = videoProcessor.getVideoMetadata(downloadPath.toAbsolutePath().toString());

    System.out.println(result);

    List<Stream> streams = new ArrayList<>();

    for (FFmpegStream ffmpegStream : result.streams) {
      Stream stream = new Stream(null);

      stream.setIndex(ffmpegStream.index);

      stream.setBitRate(ffmpegStream.bit_rate);
      stream.setFrames(ffmpegStream.nb_frames);
      stream.setAverageFrameRate(ffmpegStream.avg_frame_rate);
      stream.setSampleRate(ffmpegStream.sample_rate);

      stream.setCodecType(CodecType.valueOf(ffmpegStream.codec_type.toString()));
      stream.setCodecLongName(ffmpegStream.codec_long_name);
      stream.setCodecName(ffmpegStream.codec_name);
      stream.setCodecTag(ffmpegStream.codec_tag_string);

      stream.setAspectRatio(ffmpegStream.display_aspect_ratio);

      stream.setDuration(ffmpegStream.duration);
      stream.setDurationTs(ffmpegStream.duration_ts);

      stream.setWidth(ffmpegStream.width);
      stream.setHeight(ffmpegStream.height);

      stream.setPixFmt(ffmpegStream.pix_fmt);
      stream.setProfile(ffmpegStream.profile);

      stream.setChannels(ffmpegStream.channels);
      stream.setChannelLayout(ffmpegStream.channel_layout);

      if (ffmpegStream.tags != null && ffmpegStream.tags.get("language") != null) {
        try {

          Locale locale = Locale.of(ffmpegStream.tags.get("language").toUpperCase());

          // IsoCountryCode language = IsoCountryCode.valueOf();
          // stream.setLang(ffmpegStream.tags.get("language"));
          if (locale != null) {
            stream.setLang(locale);

          }
        } catch (IllegalArgumentException e) {

        }
      }

      stream.setTags(ffmpegStream.tags);

      streams.add(stream);
    }

    Video video = new Video(null);

    video.setBitRate(result.format.bit_rate);
    video.setDuration(result.format.duration);
    video.setFormatName(result.format.format_name);
    video.setSize(result.format.size);
    video.setStartTime(result.format.start_time);
    video.setUpload(upload);
    video.setProject(upload.getProject());
    video.setStreamsCount(result.format.nb_streams);
    video.setStreams(streams);

    this.videosRepository.save(video);

    this.videoProcessor.generatePreviews(downloadPath.toAbsolutePath().toString());

    return;
  }
}
