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
@Table(name = "image")
public class Image implements Serializable {
    @Id
    @Column(name = "image_seq")
    private String imageSeq;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "image_type_seq")
    private Integer imageTypeSeq;
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

    public static Image initFrom(Image image) {
        image.setImageSeq("IMAGE-" + UtilService.generateRandomString());
        image.setUserSeq(CurrentUser.getUser().getUserSeq());
        image.setStatusSeq(Status.APPROVED.getStatusSeq());
        return image;
    }
}
