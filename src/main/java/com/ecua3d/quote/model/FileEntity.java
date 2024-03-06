package com.ecua3d.quote.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "quott_file", schema = "quote")
public class FileEntity extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generador")
    @SequenceGenerator(name = "generador", schema = "quote", sequenceName = "quote.quots_file", allocationSize = 1)
    @Column(name = "file_id")
    private Integer fileId;
    @Column(name = "name_file")
    private String nameFile;
    private String location;

}
