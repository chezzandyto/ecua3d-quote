package com.ecua3d.quote.repository;

import com.ecua3d.quote.model.FileEntity;
import org.springframework.data.repository.CrudRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface IFileRepository extends CrudRepository<FileEntity, Integer> {
    Boolean existsByNameFile(String nameFile);
    List<FileEntity> findByNameFile(String fileName);
    FileEntity findByFileId(Integer fileId);
}
