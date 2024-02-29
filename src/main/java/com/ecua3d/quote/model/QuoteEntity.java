package com.ecua3d.quote.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "quott_quote", schema = "quote")
public class QuoteEntity extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generador1")
    @SequenceGenerator(name = "generador1", schema = "quote", sequenceName = "quote.quots_quote", allocationSize = 1)
    @Column(name = "quote_id")
    private Integer quoteId;
    private String name;
    private String email;
    private String phone;
    @Column(name = "filament_id")
    private Integer filamentId;
    @Column(name = "material_id")
    private Integer materialId;
    @Column(name = "color_id")
    private Integer colorId;
    @Column(name = "quality_id")
    private Integer qualityId;
    private Integer state;
    @OneToMany
    @JoinTable(
            name="quott_quote_file",
            schema = "quote",
            joinColumns = @JoinColumn( name="quote_id"),
            inverseJoinColumns = @JoinColumn( name="file_id")
    )
    private List<FileEntity> files;
}
