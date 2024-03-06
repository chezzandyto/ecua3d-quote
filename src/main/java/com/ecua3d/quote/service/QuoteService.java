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
import com.ecua3d.quote.vo.constant.QuoteState;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class QuoteService implements IQuoteService{

    @Value("${cloud.aws.base-folder}")
    private String baseFolder;
    @Autowired
    private IQuoteRepository iQuoteRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    private IFileService iFileService;

    @Autowired
    private EmailService emailService;

    @Override
    public List<QuoteResponse> findAll() {
        List<QuoteEntity> quoteEntities = (List<QuoteEntity>) iQuoteRepository.findAll();
        return quoteEntities.stream().map(this::convertToQuoteResponse).collect(Collectors.toList());
    }

    @Override
    public List<QuoteResponse> findAllByState(Integer state) {
        List<QuoteEntity> quoteEntities = (List<QuoteEntity>) iQuoteRepository.findAllByState(state);
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
                .files(quoteEntity.getFiles().stream().map(fileEntity -> iFileService.convertToFileResponse(fileEntity)).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public QuoteResponse saveNewQuote(QuoteDTO quoteDTO) throws EntityExistsException, IOException, MessagingException {
        QuoteEntity newEntity = new QuoteEntity();
        newEntity.setName(quoteDTO.getName());
        newEntity.setEmail(quoteDTO.getEmail());
        newEntity.setPhone(quoteDTO.getPhone());
        newEntity.setFilamentId(quoteDTO.getFilamentId());
        newEntity.setMaterialId(quoteDTO.getMaterialId());
        newEntity.setColorId(quoteDTO.getColorId());
        newEntity.setQualityId(quoteDTO.getQualityId());
        newEntity.setComment(quoteDTO.getComment());
        newEntity.setState(QuoteState.PENDING.value);
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
        try {
            emailService.sendEmail(newEntity.getEmail(),newEntity.getName(), files.stream().map(FileEntity::getNameFile).collect(Collectors.toList()));
            emailService.sendEmailToCompany(newEntity.getQuoteId(),newEntity.getName(),newEntity.getEmail(), newEntity.getPhone(), files.stream().map(FileEntity::getNameFile).collect(Collectors.toList()), newEntity.getFilamentId(), newEntity.getQualityId(),newEntity.getComment());
        } catch (Exception e) {
            log.error("No se pudo enviar mail con quoteId: {} al mail: {}",newEntity.getQuoteId(), newEntity.getEmail());
            log.error(e.getMessage());
            e.printStackTrace();
        }

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
