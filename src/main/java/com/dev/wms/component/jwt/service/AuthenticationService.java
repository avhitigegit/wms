package com.dev.wms.component.jwt.service;

import com.dev.wms.common.AppConstant;
import com.dev.wms.common.UtilService;
import com.dev.wms.common.enums.Role;
import com.dev.wms.common.enums.Status;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.model.User;
import com.dev.wms.repository.UserRepository;
import com.dev.wms.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);

    private final UtilService utilService;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;

    public AuthenticationService(UtilService utilService,
                                 UserRepository userRepository,
                                 JwtTokenUtil jwtTokenUtil,
                                 EmailService emailService) {
        this.utilService = utilService;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.emailService = emailService;
    }

    //Generate a new user and send email
    public User registerUserAndSendEmail(User user) {
        LOGGER.info("Enter registerUserAndSendEmail() in AuthenticationService.");
        if (this.utilService.checkUserAvailability(user.getEmail()) == false) {
            user = User.initFrom(user);
            this.userRepository.saveAndFlush(user);
            String token = this.jwtTokenUtil.generateTokenWithPinAndEmail(user);
            String emailBody = AppConstant.EMAIL_VERIFICATION_BODY + token;
            emailService.sendEmail(user.getEmail(), AppConstant.EMAIL_VERIFICATION_SUBJECT, emailBody);
            user.setPasswordVerifiedCode(emailBody);
            return user;
        } else if (this.userRepository.findByEmailAndStatusSeq(user.getEmail(), Status.PENDING.getStatusSeq()).getEmail().equals(user.getEmail())) {
            user = this.userRepository.findByEmailAndStatusSeq(user.getEmail(), Status.PENDING.getStatusSeq());
            this.userRepository.save(user);
            String token = jwtTokenUtil.generateTokenWithPinAndEmail(user);
            String emailBody = AppConstant.EMAIL_VERIFICATION_BODY + token;
            emailService.sendEmail(user.getEmail(), AppConstant.EMAIL_VERIFICATION_SUBJECT, emailBody);
            user.setPasswordVerifiedCode(emailBody);
            return user;
        } else {
            throw new BadRequestException("The Request Cannot Be Fulfilled Due To Bad Syntax Exception.");
        }
    }

    //Email verification request
    public User emailVerificationFromRegisteredUser(String token) {
        LOGGER.info("Enter emailVerificationFromRegisteredUser() in UserService.");
        List<String> UserEmailAndPinFromToken = jwtTokenUtil.getUserEmailAndPinFromToken(token);
        String generatedPinOfToken = UserEmailAndPinFromToken.get(0);
        String userEmailOfToken = UserEmailAndPinFromToken.get(1);
        User user = userRepository.findByEmailAndStatusSeq(userEmailOfToken, Status.PENDING.getStatusSeq());
        if (user != null) {
            if (utilService.matchingEmailGeneratedPinWithActual(userEmailOfToken, generatedPinOfToken) == true && user.getStatusSeq().equals(Status.PENDING.getStatusSeq())) {
                user.setIsEmailVerified(true);
                user.setStatusSeq(Status.APPROVED.getStatusSeq());
                user.setRoleSeq(Role.VENDER.getRoleSeq());
                userRepository.saveAndFlush(user);
                user = userRepository.findByEmailAndStatusSeq(userEmailOfToken, Status.APPROVED.getStatusSeq());
            } else {
                throw new BadRequestException("The Request Cannot Be Fulfilled Due To Bad Syntax Exception.");
            }
        } else {
//            throw new BadResponseException("Resource Not Found Exception.");
        }
        return user;
    }
}
