package Swyp8.Team12.global.common.objectstorage;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ObjectStorageService {

    @Value("${spring.s3.endpoint}")
    private String endPoint;

    @Value("${spring.s3.region}")
    private String region;

    @Value("${spring.s3.accessKey}")
    private String accessKey;

    @Value("${spring.s3.secretKey}")
    private String secretKey;

    @Value("${spring.s3.bucket}")
    private String bucketName;

    private AmazonS3 s3;

    @PostConstruct
    public void init() {
        this.s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withPathStyleAccessEnabled(true)
                .build();
    }


    public String saveToS3(MultipartFile file, String folderPath, String fileName) throws IOException {
        String savePath = folderPath + "/" + fileName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            s3.putObject(new PutObjectRequest(bucketName, savePath, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return savePath;
        } catch (SdkClientException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    public void deleteFromS3(String folderPath) {
        try {
            ObjectListing objectListing = s3.listObjects(bucketName, folderPath);
            List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();

            for (S3ObjectSummary objectSummary : objectSummaries) {
                s3.deleteObject(bucketName, objectSummary.getKey());
            }
        } catch (SdkClientException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

