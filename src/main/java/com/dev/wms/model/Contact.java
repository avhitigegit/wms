package com.dev.wms.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "contact")
public class Contact implements Serializable {
    @Id
    @Column(name = "contact_seq")
    private String contactSeq;
    @Column(name = "contact_01")
    private String contact_1;
    @Column(name = "contact_02")
    private String contact_2;
    @Column(name = "contact_03")
    private String contact_3;
    @Column(name = "contact_type_seq")
    private Integer contactTypeSeq;
    @Column(name = "status_seq")
    private Integer statusSeq;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
