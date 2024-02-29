package com.ecua3d.quote.service;

import com.ecua3d.quote.cloud.AmazonS3Service;
import com.ecua3d.quote.exception.EntityExistsException;
import com.ecua3d.quote.exception.EntityNoExistsException;
import com.ecua3d.quote.model.FileEntity;
import com.ecua3d.quote.repository.IFileRepository;
import com.ecua3d.quote.vo.FileDTO;
import com.ecua3d.quote.vo.FileDowload;
import com.ecua3d.quote.vo.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService implements IFileService{
    @Autowired
    private IFileRepository iFileRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Override
    public FileResponse convertToFileResponse(FileEntity fileEntity) {
        return FileResponse.builder()
                .fileId(fileEntity.getFileId())
                .nameFile(fileEntity.getNameFile())
                .location(fileEntity.getLocation())
                .build();
    }

    @Override
    public FileEntity saveNewFile(FileDTO fileDTO) throws EntityExistsException {
        FileEntity newEntity = new FileEntity();
        newEntity.setNameFile(fileDTO.getFileName());
        newEntity.setLocation(fileDTO.getLocation());
        return iFileRepository.save(newEntity);

    }

    @Override
    public FileDowload dowloadFile(Integer fileId) throws EntityNoExistsException {
        FileEntity fileEntity = iFileRepository.findByFileId(fileId);
        return new FileDowload(fileEntity.getNameFile(), amazonS3Service.downloadFile(fileEntity.getLocation()));
    }
}
