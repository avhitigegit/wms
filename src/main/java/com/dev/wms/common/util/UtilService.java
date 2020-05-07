package com.dev.wms.common;

import com.dev.wms.common.enums.Status;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.model.User;
import com.dev.wms.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class UtilService {

    private final UserRepository userRepository;

    public UtilService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Generate four digit code
    public static String generateFourDigitCode() {
        Random random = new Random();
        Integer fourDigitCode = random.nextInt(10000);
        return fourDigitCode.toString();
    }

    //Generate random String
    public static String generateRandomString() {
        String randomString = UUID.randomUUID().toString().replace("-", "");
        return randomString;
    }

    //Create the Password
    public static String bCryptPassword(String password) {
        if (password != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            return hashedPassword;
        } else {
            throw new BadRequestException("Does Not Pass The Correct Password.");
        }
    }

    //Check the currently logged user's role
//    public String getRoleOfCurrentUser() {
//        String status = null;
//        User user = userRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getId(), AppConstant.ACTIVE);
//        String role = user.getRole();
//        if (role.equals("ADMIN")) {
//            status = "ADMIN";
//            return status;
//        } else if (role.equals("STANDARD USER")) {
//            status = "STANDARD USER";
//            return status;
//        } else if (role.equals("PENDING")) {
//            status = "PENDING";
//            return status;
//        } else {
//            throw new BadResponseException("Role Not Found Exception");
//        }
//    }

    //Check user availability
    public boolean checkUserAvailability(String email) {
        Boolean exist;
        User user = userRepository.findByEmailAndStatusSeq(email, Status.APPROVED.getStatusSeq());
        exist = user != null;
        return exist;
    }

    //Checking email generated pin with actual pin
    public Boolean matchingEmailGeneratedPinWithActual(String email, String pinFromEmail) {
        Boolean status = null;
        User user = userRepository.findByEmailAndStatusSeq(email, Status.PENDING.getStatusSeq());
        String pinFromDatabase = user.getEmailVerifiedCode();
        status = pinFromDatabase.equalsIgnoreCase(pinFromEmail);
        if (status == true) {
            return status;
        } else {
            throw new BadRequestException("Invalid Pin.");
        }
    }
}
