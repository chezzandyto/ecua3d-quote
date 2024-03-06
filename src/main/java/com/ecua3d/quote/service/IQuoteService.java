package com.ecua3d.quote.service;

import com.ecua3d.quote.exception.EntityExistsException;
import com.ecua3d.quote.exception.EntityNoExistsException;
import com.ecua3d.quote.model.QuoteEntity;
import com.ecua3d.quote.vo.QuoteDTO;
import com.ecua3d.quote.vo.QuoteResponse;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface IQuoteService {
    List<QuoteResponse> findAll();
    List<QuoteResponse> findAllByState(Integer state);
    QuoteResponse convertToQuoteResponse(QuoteEntity quoteEntity);
    QuoteResponse saveNewQuote(QuoteDTO quoteDTO) throws EntityExistsException, IOException, MessagingException;
    QuoteEntity findByQuoteId(Integer quoteId) throws EntityNoExistsException;
}
