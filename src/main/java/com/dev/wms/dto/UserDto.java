package com.dev.wms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto implements Serializable {
    private String userSeq;
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String otherNames;
    private String title;
    private String descriptionPictureUrl;
    private String emailVerifiedCode;
    private String passwordVerifiedCode;
    private Boolean isEmailVerified;
    private Boolean isPasswordReset;
    private LocalDateTime dateOfBirth;
    private Integer statusSeq;
    private Integer roleSeq;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
