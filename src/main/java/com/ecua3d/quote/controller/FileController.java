package com.ecua3d.quote.controller;

import com.ecua3d.quote.exception.EntityNoExistsException;
import com.ecua3d.quote.service.IFileService;
import com.ecua3d.quote.vo.FileDowload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
@Controller
@ResponseBody
@RequestMapping("/file")
public class FileController {
    @Autowired
    private IFileService iFileService;


    @GetMapping(value = "/downloadS3File/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<ByteArrayResource> downloadS3File(@PathVariable Integer fileId) throws EntityNoExistsException {
        FileDowload data = iFileService.dowloadFile(fileId);
        ByteArrayResource resource = new ByteArrayResource(data.getBytes());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Content-disposition", Collections.singletonList("attachment; filename=\"" + data.getFileName() + "\""));
        return new ResponseEntity<ByteArrayResource>(resource, headers, HttpStatus.OK);
    }

}
