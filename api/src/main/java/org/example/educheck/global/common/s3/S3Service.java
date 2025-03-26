package org.example.educheck.global.common.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.custom.common.ServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private static final String FILE_PATH_PREFIX = "attendance-absences/";
    //버킷명, 리전, 파일경로
    private static final String IMAGE_URL_FORMAT = "https://%s.s3.%s.amazonaws.com/%s";
    private final S3Client s3Client;
    @Value("${BUCKET_NAME}")
    private String bucketName;
    @Value("${REGION}")
    private String region;

    public List<Map<String, String>> uploadFiles(MultipartFile[] files) {
        List<Map<String, String>> uploadedFiles = new ArrayList<>();


        for (MultipartFile file : files) {
            Map<String, String> fileInfo = uploadFile(file);
            uploadedFiles.add(fileInfo);

        }
        return uploadedFiles;
    }

    private Map<String, String> uploadFile(MultipartFile file) {
        String s3Key = FILE_PATH_PREFIX + UUID.randomUUID() + "_" + file + file.getOriginalFilename();

        uploadFileToS3(file, s3Key);

        String fileUrl = String.format(IMAGE_URL_FORMAT, bucketName, region, s3Key);

        return Map.of(
                "fileUrl", fileUrl,
                "s3Key", s3Key
        );
    }

    private void uploadFileToS3(MultipartFile file, String s3Key) {

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException | S3Exception e) {
            log.error(e.getMessage());
            throw new ServerErrorException(ErrorCode.FILE_UPLOAD_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServerErrorException(ErrorCode.FILE_UPLOAD_ERROR);
        }

    }

    public void deleteFile(String s3Key) {
        log.info("동작");
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServerErrorException(ErrorCode.FILE_DELETE_ERROR);
        }
    }


}

