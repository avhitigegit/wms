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
@Table(name = "description")
public class Description implements Serializable {
    @Id
    @Column(name = "description_seq")
    private String descriptionSeq;
    @Column(name = "website")
    private String website;
    @Column(name = "facebook")
    private String facebook;
    @Column(name = "linkedin")
    private String linkedin;
    @Column(name = "description_1")
    private String description_1;
    @Column(name = "description_2")
    private String description_2;
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

    public static Description initFrom(Description description) {
        description.setDescriptionSeq("DESCRIPTION-" + UtilService.generateRandomString());
        description.setUserSeq(CurrentUser.getUser().getUserSeq());
        description.setStatusSeq(Status.APPROVED.getStatusSeq());
        return description;
    }
}