package com.ecua3d.quote.cloud;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import java.io.FileOutputStream;

@Service
public class AmazonS3Service {
    @Value("${cloud.aws.bucketName}")
    private String bucketName;
    private static final Logger log = LoggerFactory.getLogger(AmazonS3Service.class);

    @Autowired
    private AmazonS3 s3Client;

    public void uploadFile(MultipartFile file, String fileName) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        convertedFile.delete();
    }


    private File convertMultipartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }


    public byte[] downloadFile(String pathFile) {
        S3Object s3Object = s3Client.getObject(bucketName, pathFile);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
