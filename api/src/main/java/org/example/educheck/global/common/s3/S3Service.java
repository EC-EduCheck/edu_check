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
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
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

    //파일을 S3에 업로드하고 URL과 객체키를 반환한다.
    public Map<String, String> uploadFile(MultipartFile file) {
        String s3Key = FILE_PATH_PREFIX + UUID.randomUUID() + "_" + file + file.getOriginalFilename();

        //file을 S3Key 경로(고유 식별자)에 업로드한다.
        uploadFileToS3(file, s3Key);

        //접근 가능한 url 생성
        String fileUrl = String.format(IMAGE_URL_FORMAT, bucketName, region, s3Key);

        //URL과 S3Key를 Map으로 반환한다.
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
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServerErrorException(ErrorCode.FILE_UPLOAD_ERROR);
        }

    }

}

