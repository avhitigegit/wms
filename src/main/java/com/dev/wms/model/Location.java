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
@Table(name = "location")
public class Location implements Serializable {
    @Id
    @Column(name = "location_seq")
    private String locationSeq;
    @Column(name = "zip_code")
    private Integer zipCode;
    @Column(name = "street_01")
    private String street_01;
    @Column(name = "street_02")
    private String street_02;
    @Column(name = "street_03")
    private String street_03;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "latitudes")
    private String latitudes;
    @Column(name = "longitudes")
    private String longitudes;
    @Column(name = "status_seq")
    private Integer statusSeq;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}