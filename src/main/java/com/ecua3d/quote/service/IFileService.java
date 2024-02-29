package com.ecua3d.quote.service;

import com.ecua3d.quote.exception.EntityExistsException;
import com.ecua3d.quote.exception.EntityNoExistsException;
import com.ecua3d.quote.model.FileEntity;
import com.ecua3d.quote.vo.FileDTO;
import com.ecua3d.quote.vo.FileResponse;

import java.util.List;

public interface IFileService {
    List<FileResponse> findAll();
    FileResponse convertToFileResponse(FileEntity fileEntity);
    FileEntity saveNewFile(FileDTO fileDTO) throws EntityExistsException;
    FileEntity findByFileId(Integer fileId) throws EntityNoExistsException;

}
