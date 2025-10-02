package com.zyx.vps.infra.http.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import software.amazon.awssdk.core.retry.RetryPolicy;
import com.zyx.vps.application.entities.SymmetricEncryption;
import com.zyx.vps.application.entities.local.SymmetricEncryptionLocal;
import com.zyx.vps.application.repositories.SymmetricEncryptionRepository;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.batch.BatchAsyncClient;
import software.amazon.awssdk.services.batch.model.AssignPublicIp;
import software.amazon.awssdk.services.batch.model.ContainerProperties;
import software.amazon.awssdk.services.batch.model.JobDefinitionType;
import software.amazon.awssdk.services.batch.model.NetworkConfiguration;
import software.amazon.awssdk.services.batch.model.PlatformCapability;
import software.amazon.awssdk.services.batch.model.RegisterJobDefinitionRequest;
import software.amazon.awssdk.services.batch.model.ResourceRequirement;
import software.amazon.awssdk.services.batch.model.ResourceType;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("test")
public class TesteController {

    @Autowired
    private SymmetricEncryptionRepository symmetricEncryptionRepository;

    @GetMapping
    public List<String> test(@RequestParam String message) {
        List<SymmetricEncryption> symmetricEncryptions = this.symmetricEncryptionRepository.findAll();

        return symmetricEncryptions.stream().map(s -> s.decrypt(message)).toList();
    }

    @PostMapping
    public void testePost() {
        String executionRoleARN = "";
        String image = "";
        String cpuArch = "";

        SdkAsyncHttpClient httpClient = NettyNioAsyncHttpClient.builder()
                .maxConcurrency(100) // Increase max concurrency to handle more simultaneous connections.
                .connectionTimeout(Duration.ofSeconds(60)) // Set the connection timeout.
                .readTimeout(Duration.ofSeconds(60)) // Set the read timeout.
                .writeTimeout(Duration.ofSeconds(60)) // Set the write timeout.
                .build();

        ClientOverrideConfiguration overrideConfig = ClientOverrideConfiguration.builder()
                .apiCallTimeout(Duration.ofMinutes(2)) // Set the overall API call timeout.
                .apiCallAttemptTimeout(Duration.ofSeconds(90)) // Set the individual call attempt timeout.
                // .retryPolicy(RetryPolicy.builder() // Add a retry policy to handle transient
                // errors.
                // .numRetries(3) // Number of retry attempts.
                // .build())

                .build();

        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider
                .create(AwsBasicCredentials.create("01", "02"));

        BatchAsyncClient batchClient = BatchAsyncClient.builder()
                .region(Region.US_EAST_1)
                .httpClient(httpClient)
                .overrideConfiguration(overrideConfig).credentialsProvider(credentialsProvider)
                .build();

        NetworkConfiguration networkConfiguration = NetworkConfiguration.builder()
                .assignPublicIp(AssignPublicIp.ENABLED)
                .build();

        ContainerProperties containerProperties = ContainerProperties.builder()
                .image(image)
                .executionRoleArn(executionRoleARN)
                .resourceRequirements(
                        Arrays.asList(
                                ResourceRequirement.builder()
                                        .type(ResourceType.VCPU)
                                        .value("16")
                                        .build(),
                                ResourceRequirement.builder()
                                        .type(ResourceType.MEMORY)
                                        .value("32768")
                                        .build()))
                .networkConfiguration(networkConfiguration)
                .runtimePlatform(b -> b
                        .cpuArchitecture(cpuArch)
                        .operatingSystemFamily("LINUX"))
                .build();

        RegisterJobDefinitionRequest request = RegisterJobDefinitionRequest.builder()
                .jobDefinitionName("teste")
                .type(JobDefinitionType.CONTAINER)
                .containerProperties(containerProperties)
                .platformCapabilities(PlatformCapability.FARGATE)
                .build();

    }

}
