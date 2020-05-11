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
@Table(name = "type")
public class Type implements Serializable {
    @Id
    @Column(name = "type_seq")
    private String typeSeq;
    @Column(name = "name")
    private String name;
    @Column(name = "type_name")
    private String typeName;
    @Column(name = "logo_url")
    private String logo;
    @Column(name = "status_seq")
    private Integer statusSeq;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}