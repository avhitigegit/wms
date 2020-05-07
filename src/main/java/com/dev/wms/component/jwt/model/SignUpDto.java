package com.dev.wms.component.jwt.model;

import com.dev.wms.exception.BadRequestException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    private String email;
    private String password;
//    private String firstName;

    public static String passwordvalidation(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (password.matches(pattern)) {
            return password;
        } else {
            throw new BadRequestException("The Password Not Validate Exception.");
        }
    }

//            ^       # start-of-string
//  (?=.*[0-9])       # a digit must occur at least once
//  (?=.*[a-z])       # a lower case letter must occur at least once
//  (?=.*[A-Z])       # an upper case letter must occur at least once
//  (?=.*[@#$%^&+=])  # a special character must occur at least once
//  (?=\S+$)          # no whitespace allowed in the entire string
//  .{8,}             # anything, at least eight places though
//            $       # end-of-string

}
