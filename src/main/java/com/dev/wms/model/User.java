package com.dev.wms.model;

import com.dev.wms.common.enums.Status;
import com.dev.wms.common.util.UtilService;
import com.dev.wms.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private List<Contact> contactList;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private List<Description> descriptionList;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private List<Image> imageList;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private List<Location> locationList;

    @Transient
    private List<String> contactListSeq;
    @Transient
    private List<String> descriptionListSeq;
    @Transient
    private List<String> imageListSeq;
    @Transient
    private List<String> locationListSeq;

    public static UserDto setUserInRegistration(User dbUser, UserDto userDto) {
        userDto.setUserSeq(dbUser.getUserSeq());
        userDto.setEmail(dbUser.getEmail());
        userDto.setPassword(dbUser.getPassword());
        userDto.setEmailVerifiedCode(dbUser.getEmailVerifiedCode());
        userDto.setPasswordVerifiedCode(dbUser.getPasswordVerifiedCode());
        userDto.setIsEmailVerified(dbUser.getIsEmailVerified());
        userDto.setIsPasswordReset(dbUser.getIsPasswordReset());
        userDto.setStatusSeq(dbUser.getStatusSeq());
        userDto.setRoleSeq(dbUser.getRoleSeq());
        userDto.setCreatedAt(dbUser.getCreatedAt());
        userDto.setUpdatedAt(dbUser.getUpdatedAt());

//        userDto.setFirstName(dbUser.getFirstName());
//        userDto.setMiddleName(dbUser.getMiddleName());
//        userDto.setLastName(dbUser.getLastName());
//        userDto.setOtherNames(dbUser.getOtherNames());
//        userDto.setTitle(dbUser.getTitle());
//        userDto.setDescriptionPictureUrl(dbUser.getDescriptionPictureUrl());
//        userDto.setDateOfBirth(dbUser.getDateOfBirth());

        return userDto;
    }

    public List<String> getContactListSeq() {
        if (this.contactListSeq != null) {
            return this.contactListSeq;
        }
        if (this.contactList != null) {
            for (Contact contact : contactList) {
                contactListSeq.add(contact.getContactSeq());
            }
            return contactListSeq;
        }
        return contactListSeq;
    }

    public void setContactListSeq(List<String> contactListSeq) {
        this.contactListSeq = contactListSeq;
    }

    public List<String> getDescriptionListSeq() {
        if (this.descriptionListSeq != null) {
            return this.descriptionListSeq;
        }
        if (this.descriptionList != null) {
            for (Description description : descriptionList) {
                descriptionListSeq.add(description.getDescriptionSeq());
            }
            return descriptionListSeq;
        }
        return descriptionListSeq;
    }

    public void setDescriptionListSeq(List<String> descriptionListSeq) {
        this.descriptionListSeq = descriptionListSeq;
    }

    public List<String> getImageListSeq() {
        if (this.imageListSeq != null) {
            return this.imageListSeq;
        }
        if (this.imageList != null) {
            for (Image image : imageList) {
                imageListSeq.add(image.getImageSeq());
            }
            return imageListSeq;
        }
        return imageListSeq;
    }

    public void setImageListSeq(List<String> imageListSeq) {
        this.imageListSeq = imageListSeq;
    }

    public List<String> getLocationListSeq() {
        if (this.locationListSeq != null) {
            return this.locationListSeq;
        }
        if (this.locationList != null) {
            for (Location location : locationList) {
                locationListSeq.add(location.getLocationSeq());
            }
            return locationListSeq;
        }
        return locationListSeq;
    }

    public static User initFrom(User user) {
        user.setPassword(UtilService.bCryptPassword(user.getPassword()));
        user.setUserSeq("USER-" + UtilService.generateRandomString());
        user.setStatusSeq(Status.PENDING.getStatusSeq());
        user.setEmailVerifiedCode(UtilService.generateRandomString());
        user.setIsEmailVerified(false);
        return user;
    }

    public void setLocationListSeq(List<String> locationListSeq) {
        this.locationListSeq = locationListSeq;
    }
}
