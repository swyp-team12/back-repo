package Swyp8.Team12.global.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String bucket;

    /**
     * Base64 인코딩된 이미지를 S3에 업로드
     */
    public String uploadBase64Image(String base64Image) throws IOException {
        if (base64Image == null || base64Image.isEmpty()) {
            log.warn("Base64 이미지 데이터가 비어있거나 null입니다.");
            return null;
        }

        log.info("Base64 이미지 업로드 시작: 데이터 길이={}", base64Image.length());

        try {
            // Base64 형식에서 실제 이미지 데이터 추출 (data:image/jpeg;base64, 부분 제거)
            String imageData;
            String contentType = "image/jpeg"; // 기본값
            
            if (base64Image.contains(",")) {
                String[] parts = base64Image.split(",");
                imageData = parts[1];
                
                // 콘텐츠 타입 추출 시도
                if (parts[0].contains("data:") && parts[0].contains(";base64")) {
                    contentType = parts[0].substring(5, parts[0].indexOf(";base64"));
                    log.info("감지된 콘텐츠 타입: {}", contentType);
                }
            } else {
                imageData = base64Image;
            }
            
            // Base64 디코딩
            byte[] imageBytes = Base64.getDecoder().decode(imageData);
            log.info("Base64 디코딩 완료: 바이트 수={}", imageBytes.length);
            
            // 파일 이름 생성
            String extension = contentType.contains("/") ? 
                    contentType.substring(contentType.indexOf("/") + 1) : "jpg";
            String fileName = UUID.randomUUID().toString() + "." + extension;
            
            // 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageBytes.length);
            metadata.setContentType(contentType);
            
            log.info("S3 업로드 시도: 버킷={}, 파일명={}", bucket, fileName);
            
            // S3에 업로드
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            
            String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
            log.info("S3 업로드 성공: URL={}", fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("Base64 이미지 업로드 실패: {}", e.getMessage(), e);
            throw new IOException("Base64 이미지 업로드 중 오류 발생: " + e.getMessage(), e);
        }
    }
} 