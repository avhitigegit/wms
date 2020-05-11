package com.dev.wms.model;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.common.enums.Status;
import com.dev.wms.common.util.UtilService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    @Basic
    @Column(name = "user_seq", nullable = false, updatable = false, insertable = false)
    private String userSeq;

    public static Location initFrom(Location location) {
        location.setLocationSeq("LOCATION-" + UtilService.generateRandomString());
        location.setUserSeq(CurrentUser.getUser().getUserSeq());
        location.setStatusSeq(Status.APPROVED.getStatusSeq());
        return location;
    }
}