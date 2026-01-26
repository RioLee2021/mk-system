package net.system.mk.commons.utils;

import cn.hutool.core.util.IdUtil;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/26/0026 20:47
 */
public class CloudflareR2Client {

    private final S3Client s3Client;

    private final S3Config config;

    public CloudflareR2Client(S3Config config) {
        this.s3Client = buildS3Client(config);
        this.config  = config;
    }

    /**
     * Configuration class for R2 credentials and endpoint
     * - accountId: Your Cloudflare account ID
     * - accessKey: Your R2 Access Key ID (see: https://developers.cloudflare.com/r2/api/tokens)
     * - secretKey: Your R2 Secret Access Key (see: https://developers.cloudflare.com/r2/api/tokens)
     */
    public static class S3Config {
        private final String accountId="ac9dc3a04e25c298cae499ec0d585349";
        private final String accessKey="07efb3c89087cb34a11cbef10e15eae8";
        private final String secretKey="e41ecee04b4883888d3e70e926b56b5d3cc754b37725296f801c464bebbad462";
        private final String endpoint;

        public S3Config() {
            this.endpoint = String.format("https://%s.r2.cloudflarestorage.com", accountId);
        }

        public String getAccessKey() { return accessKey; }
        public String getSecretKey() { return secretKey; }
        public String getEndpoint() { return endpoint; }
    }

    /**
     * Builds and configures the S3 client with R2-specific settings
     */
    private static S3Client buildS3Client(S3Config config) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                config.getAccessKey(),
                config.getSecretKey()
        );

        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        return S3Client.builder()
                .endpointOverride(URI.create(config.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto")) // Required by SDK but not used by R2
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    /**
     * Lists all buckets in the R2 storage
     */
    public List<Bucket> listBuckets() {
        try {
            return s3Client.listBuckets().buckets();
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to list buckets: " + e.getMessage(), e);
        }
    }

    /**
     * Lists all objects in the specified bucket
     */
    public List<S3Object> listObjects(String bucketName) {
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            return s3Client.listObjectsV2(request).contents();
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to list objects in bucket " + bucketName + ": " + e.getMessage(), e);
        }
    }

    public String uploadFile(String bucketName, String directory, MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String filename = IdUtil.fastSimpleUUID();
        if (originalFilename != null) {
            int lastDotIndex = originalFilename.lastIndexOf(".");
            if (lastDotIndex > 0) {
                filename += originalFilename.substring(lastDotIndex);
            }
        }
        // 构建对象键，包含目录路径
        String objectKey = directory.endsWith("/") ? directory + filename : directory + "/" + filename;
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentLength(file.getSize())
                .contentType(file.getContentType())
                .build();
        try {
            PutObjectResponse rs = s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            // 构建并返回访问链接
            return String.format("%s/%s/%s",
                    config.getEndpoint(), bucketName, objectKey);
        } catch (IOException e) {
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, e.getMessage());
        }
    }

}
