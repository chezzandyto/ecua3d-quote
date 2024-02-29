package com.ecua3d.quote.repository;

import com.ecua3d.quote.model.FileEntity;
import com.ecua3d.quote.model.QuoteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IQuoteRepository extends CrudRepository<QuoteEntity, Integer> {
    List<QuoteEntity> findAll();
    Optional<QuoteEntity> findByQuoteId(Integer quoteId);
}
