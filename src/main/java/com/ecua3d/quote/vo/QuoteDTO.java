package com.ecua3d.quote.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteDTO {
    @NotBlank(message = "Nombre de area no puede ser vacío")
    @Size(message = "Nombre de area maximo de 64 caracteres y minimo 3",min= 3,max = 64)
    private String name;
    private String email;
    private String phone;
    private Integer materialId;
    private Integer colorId;
    private Integer filamentId;
    private String comment;
    private Integer qualityId;
    private List<MultipartFile> files;
}
