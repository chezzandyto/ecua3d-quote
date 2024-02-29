package com.ecua3d.quote.service;

import com.ecua3d.quote.exception.EntityExistsException;
import com.ecua3d.quote.exception.EntityNoExistsException;
import com.ecua3d.quote.model.FileEntity;
import com.ecua3d.quote.repository.IFileRepository;
import com.ecua3d.quote.vo.FileDTO;
import com.ecua3d.quote.vo.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService implements IFileService{

    @Autowired
    private IFileRepository iFileRepository;

    @Override
    public List<FileResponse> findAll() {
        List<FileEntity> fileEntities = (List<FileEntity>) iFileRepository.findAll();
        return fileEntities.stream().map(this::convertToFileResponse).collect(Collectors.toList());
    }

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
        //return convertToFileResponse(newEntity);
    }

    @Override
    public FileEntity findByFileId(Integer fileId) throws EntityNoExistsException {
        return iFileRepository.findByFileId(fileId)
                .orElseThrow(() -> new EntityNoExistsException(HttpStatus.BAD_REQUEST,"No existe"));
    }
}
