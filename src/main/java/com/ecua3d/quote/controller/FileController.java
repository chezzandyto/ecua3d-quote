package com.ecua3d.quote.controller;

import com.ecua3d.quote.exception.EntityExistsException;
import com.ecua3d.quote.service.IFileService;
import com.ecua3d.quote.vo.FileDTO;
import com.ecua3d.quote.vo.FileResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/file")
public class FileController {
    @Autowired
    private IFileService iFileService;

    @GetMapping
    public ResponseEntity<List<FileResponse>> getAllFile(){
        return new ResponseEntity<List<FileResponse>>(iFileService.findAll(), HttpStatus.OK);
    }
}
