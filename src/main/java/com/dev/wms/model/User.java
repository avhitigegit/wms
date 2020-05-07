package com.dev.wms.model;

import com.dev.wms.common.UtilService;
import com.dev.wms.common.enums.Role;
import com.dev.wms.common.enums.Status;
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
@Table(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "user_seq")
    private String userSeq;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "other_name")
    private String otherNames;
    @Column(name = "title")
    private String title;
    @Column(name = "dp_url")
    private String descriptionPictureUrl;
    @Column(name = "email_verified_code")
    private String emailVerifiedCode;
    @Column(name = "password_verified_code")
    private String passwordVerifiedCode;
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;
    @Column(name = "is_password_reset")
    private Boolean isPasswordReset;
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;
    @Column(name = "status_seq")
    private Integer statusSeq;
    @Column(name = "role_seq")
    private Integer roleSeq;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static User initFrom(User user) {
        user.setPassword(UtilService.bCryptPassword(user.getPassword()));
        user.setUserSeq("USER-" + UtilService.generateRandomString());
        user.setStatusSeq(Status.PENDING.getStatusSeq());
        user.setEmailVerifiedCode(UtilService.generateRandomString());
        user.setIsEmailVerified(false);
        user.setRoleSeq(Role.VENDER.getRoleSeq());
        return user;
    }
}
