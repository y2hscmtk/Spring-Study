package com.s3_practice.s3_practice.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client s3Client;

    @Value("${s3.bucket}")
    private String bucket;

    // 파일 업로드 메서드
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(bucket, fileName, inputStream, metadata);
        }

        // S3에 업로드된 파일 URL 반환
        URL fileUrl = s3Client.getUrl(bucket, fileName);
        return fileUrl.toString();
    }

    // 파일 다운로드 메서드 - 직접 다운로드
    // -> 이미지를 파일로 다운할 수 있는 다운로드 링크를 제공한다.
    public byte[] downloadFile(String fileName) throws IOException {
        return s3Client.getObject(bucket, fileName).getObjectContent().readAllBytes();
    }

    // 파일 URL 반환 메서드 - 프론트에서 랜더링
    // -> 프론트엔드에서 이미지를 렌더링 할 수 있도록, S3 링크를 제공한다.
    public String getFileUrl(String fileName) {
        return s3Client.getUrl(bucket, fileName).toString();
    }
}