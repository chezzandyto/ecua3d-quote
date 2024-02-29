package com.ecua3d.quote.service;

import com.ecua3d.quote.cloud.AmazonS3Service;
import com.ecua3d.quote.exception.EntityExistsException;
import com.ecua3d.quote.exception.EntityNoExistsException;
import com.ecua3d.quote.model.FileEntity;
import com.ecua3d.quote.model.QuoteEntity;
import com.ecua3d.quote.repository.IQuoteRepository;
import com.ecua3d.quote.vo.FileDTO;
import com.ecua3d.quote.vo.QuoteDTO;
import com.ecua3d.quote.vo.QuoteResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteService implements IQuoteService{

    @Value("${cloud.aws.base-folder}")
    private String baseFolder;
    @Autowired
    private IQuoteRepository iQuoteRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    private IFileService iFileService;

    @Override
    public List<QuoteResponse> findAll() {
        List<QuoteEntity> quoteEntities = (List<QuoteEntity>) iQuoteRepository.findAll();
        return quoteEntities.stream().map(this::convertToQuoteResponse).collect(Collectors.toList());
    }

    @Override
    public QuoteResponse convertToQuoteResponse(QuoteEntity quoteEntity) {
        return QuoteResponse.builder()
                .quoteId(quoteEntity.getQuoteId())
                .name(quoteEntity.getName())
                .email(quoteEntity.getEmail())
                .phone(quoteEntity.getPhone())
                .filamentId(quoteEntity.getFilamentId())
                .qualityId(quoteEntity.getQualityId())
                .fileNames(quoteEntity.getFiles().stream().map(FileEntity::getNameFile).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public QuoteResponse saveNewQuote(QuoteDTO quoteDTO) throws EntityExistsException, IOException {
        QuoteEntity newEntity = new QuoteEntity();
        newEntity.setName(quoteDTO.getName());
        newEntity.setEmail(quoteDTO.getEmail());
        newEntity.setPhone(quoteDTO.getPhone());
        newEntity.setFilamentId(quoteDTO.getFilamentId());
        newEntity.setMaterialId(quoteDTO.getMaterialId());
        newEntity.setColorId(quoteDTO.getColorId());
        newEntity.setQualityId(quoteDTO.getQualityId());
        List<FileEntity> files = new ArrayList<>();
        for (MultipartFile file : quoteDTO.getFiles()) {
            String fileName = baseFolder + "/" + getDate() + "/" + (System.currentTimeMillis() / 1000) + "_" + file.getOriginalFilename();
            amazonS3Service.uploadFile(file, fileName);
            files.add(iFileService.saveNewFile(FileDTO.builder()
                            .fileName(file.getOriginalFilename())
                            .Location(fileName)
                            .build()));
        }
        newEntity.setFiles(files);
        iQuoteRepository.save(newEntity);
        return convertToQuoteResponse(newEntity);
    }

    @Override
    public QuoteEntity findByQuoteId(Integer quoteId) throws EntityNoExistsException {
        return iQuoteRepository.findByQuoteId(quoteId)
                .orElseThrow(() -> new EntityNoExistsException(HttpStatus.BAD_REQUEST,"No existe"));
    }

    private String getDate(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
