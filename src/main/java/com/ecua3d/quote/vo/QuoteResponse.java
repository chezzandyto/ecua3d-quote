package com.ecua3d.quote.vo;

import com.ecua3d.quote.model.FileEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteResponse {
    private Integer quoteId;
    private String name;
    private String email;
    private String phone;
    private Integer filamentId;
    private Integer qualityId;
    private List<FileResponse> files;
}
