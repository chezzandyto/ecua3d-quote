package com.ecua3d.quote.service;

import com.ecua3d.quote.exception.EntityExistsException;
import com.ecua3d.quote.exception.EntityNoExistsException;
import com.ecua3d.quote.model.FileEntity;
import com.ecua3d.quote.vo.FileDTO;
import com.ecua3d.quote.vo.FileDowload;
import com.ecua3d.quote.vo.FileResponse;



public interface IFileService {
    FileResponse convertToFileResponse(FileEntity fileEntity);
    FileEntity saveNewFile(FileDTO fileDTO) throws EntityExistsException;
    FileDowload dowloadFile(Integer fileId) throws EntityNoExistsException;

}
