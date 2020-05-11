package com.dev.wms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class LocationDto implements Serializable {
    private String locationSeq;
    private Integer zipCode;
    private String street_01;
    private String street_02;
    private String street_03;
    private String city;
    private String province;
    private String latitudes;
    private String longitudes;
    private Integer statusSeq;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userSeq;
}
