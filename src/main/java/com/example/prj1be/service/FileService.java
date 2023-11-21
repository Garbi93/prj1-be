package com.example.prj1be.service;

import com.example.prj1be.domain.BoardFile;
import com.example.prj1be.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class FileService {
    private final FileMapper mapper;

    private final S3Client s3;

    @Value("${image.file.prefix}")
    private String urlPrefix;


    @Value("${aws.s3.bucket.name}")
    private String bucket;


    public void remove(Integer id,Integer fileId) {
        // 파일 수정에서 삭제할 때에 aws에서 삭제 시키기
        deleteFile(id, fileId);

        mapper.deleteById(fileId);
    }


    // 파일 수정에서 삭제할 때에 aws에서 삭제 시키기
    private void deleteFile(Integer id,Integer fileId) {
        BoardFile boardFile = mapper.selectById(fileId);

        String key = "prj1/" + id + "/" + boardFile.getName();

        DeleteObjectRequest objectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3.deleteObject(objectRequest);
    }
}
