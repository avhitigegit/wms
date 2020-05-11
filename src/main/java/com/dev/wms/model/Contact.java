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
    @Basic
    @Column(name = "user_seq", nullable = false, updatable = false, insertable = false)
    private String userSeq;

    public static Contact initFrom(Contact contact) {
        contact.setContactSeq("CONTACT-" + UtilService.generateRandomString());
        contact.setUserSeq(CurrentUser.getUser().getUserSeq());
        contact.setStatusSeq(Status.APPROVED.getStatusSeq());
        return contact;
    }
}
