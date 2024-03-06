package com.ecua3d.quote.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingFields {
    @CreatedBy
    @Column(name = "created_by_user")
    private String createByUser;
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate ;
    @LastModifiedBy
    @Column(name = "last_modified_by_user")
    private String lastModifiedByUser;
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
    @Column(name = "created_from_ip")
    private String createdFromIp;
    @Column(name = "updated_from_ip")
    private String updatedFromIp;
    @Column(name = "status")
    private String status = "1";
}
