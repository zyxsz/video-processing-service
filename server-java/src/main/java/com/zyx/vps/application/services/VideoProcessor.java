package com.zyx.vps.application.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import net.bramp.ffmpeg.probe.FFmpegStream.CodecType;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

public class VideoProcessor {
  private String tmpPreviewsFolder = new File("./tmp/previews").getAbsolutePath();

  private String ffmpegPath = new File("./src/main/resources/ffmpeg/bin/ffmpeg").getAbsolutePath();
  private String ffprobePath = new File("./src/main/resources/ffmpeg/bin/ffprobe").getAbsolutePath();

  private FFmpeg ffmpeg;
  private FFprobe ffprobe;

  public VideoProcessor() {
    System.out.println(ffmpegPath);
    System.out.println(ffprobePath);

    try {
      this.ffmpeg = new FFmpeg(this.ffmpegPath);
      this.ffprobe = new FFprobe(this.ffprobePath);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public FFmpegProbeResult getVideoMetadata(String path) throws IOException {
    FFmpegProbeResult probeResult = ffprobe.probe(path);

    return probeResult;
  }

  public void generatePreviews(String videoPath) throws IOException {
    FFmpegProbeResult probe = ffprobe.probe(videoPath);

    FFmpegBuilder builder = new FFmpegBuilder();

    builder.addInput(videoPath);

    for (FFmpegStream stream : probe.streams.stream().filter(stream -> (stream.codec_type == CodecType.AUDIO)
        || (stream.codec_type == CodecType.VIDEO && stream.is_avc != null)).toList()) {

      System.out.println(stream.index);

      UUID id = UUID.randomUUID();

      FFmpegOutputBuilder output = new FFmpegOutputBuilder();

      output.addExtraArgs("-map", "0:" + stream.index);

      if (stream.codec_type == CodecType.AUDIO) {
        Path outputPath = Path.of(tmpPreviewsFolder + "/" + id + "_" + stream.index + ".m4a");
        output.setFilename(outputPath.toAbsolutePath().toString());

        output.disableVideo().setAudioCodec("aac");
      } else if (stream.codec_type == CodecType.VIDEO) {
        Path outputPath = Path.of(tmpPreviewsFolder + "/" + id + "_" + stream.index + ".mp4");
        output.setFilename(outputPath.toAbsolutePath().toString());

        output.disableAudio()
            .setVideoFilter("scale=0.5*iw:0.5*ih,setpts=0.1*PTS,framerate=5");
      }

      builder.addOutput(output);

    }

    FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);

    System.out.println("ffmpeg job started...");
    executor.createJob(builder, new ProgressListener() {

      // Using the FFmpegProbeResult determine the duration of the input
      final double duration_ns = probe.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

      @Override
      public void progress(Progress progress) {
        double percentage = progress.out_time_ns / duration_ns;

        // Print out interesting information about the progress
        System.out.println(String.format(
            "[%.0f%%] status:%s frame:%d fps:%.0f speed:%.2fx",
            percentage * 100,
            progress.status,
            progress.frame,
            progress.fps.doubleValue(),
            progress.speed));
      }
    }).run();
    System.out.println("ffmpeg job finished");
  }

}
