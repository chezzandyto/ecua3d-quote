package com.ecua3d.quote.controller;

import com.ecua3d.quote.exception.EntityExistsException;
import com.ecua3d.quote.service.IQuoteService;
import com.ecua3d.quote.vo.QuoteDTO;
import com.ecua3d.quote.vo.QuoteResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/quote")
public class QuoteController {
    @Autowired
    private IQuoteService iQuoteService;

    @GetMapping
    public ResponseEntity<List<QuoteResponse>> getAllQuote(){
        return new ResponseEntity<List<QuoteResponse>>(iQuoteService.findAll(), HttpStatus.OK);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuoteResponse> saveNewQuote(@Valid @ModelAttribute QuoteDTO body) throws EntityExistsException, IOException {
        return new ResponseEntity<>(iQuoteService.saveNewQuote(body),HttpStatus.CREATED);
    }
    @GetMapping("/{state}")
    public ResponseEntity<List<QuoteResponse>> getAllQuoteByState(@PathVariable Integer state){
        return new ResponseEntity<List<QuoteResponse>>(iQuoteService.findAllByState(state), HttpStatus.OK);
    }
}
