package com.dev.wms.component.jwt.service;

import com.dev.wms.common.AppConstant;
import com.dev.wms.common.enums.Role;
import com.dev.wms.common.enums.Status;
import com.dev.wms.common.util.UtilService;
import com.dev.wms.component.jwt.model.SignUpDto;
import com.dev.wms.dto.PasswordResetDto;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.exception.BadResponseException;
import com.dev.wms.exception.UnauthorizedException;
import com.dev.wms.model.User;
import com.dev.wms.repository.UserRepository;
import com.dev.wms.service.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);

    private final UtilService utilService;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;

    @Autowired
    public AuthenticationService(UtilService utilService,
                                 UserRepository userRepository,
                                 JwtTokenUtil jwtTokenUtil,
                                 EmailService emailService) {
        this.utilService = utilService;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.emailService = emailService;
    }

    public User registerUserAndSendEmail(SignUpDto signUpDto) {
        LOGGER.info("Enter registerUserAndSendEmail() in AuthenticationService.");
        User user = new User();
        try {
            if (signUpDto.getRole().equals(Role.CUSTOMER.getRole())) {
                user.setRoleSeq(Role.CUSTOMER.getRoleSeq());
            } else if (signUpDto.getRole().equals(Role.VENDOR.getRole())) {
                user.setRoleSeq(Role.VENDOR.getRoleSeq());
            }
            BeanUtils.copyProperties(signUpDto, user);
            if (this.utilService.checkUserAvailability(user.getEmail()) == false) {
                user = User.initFrom(user);
                this.userRepository.saveAndFlush(user);
                String token = this.jwtTokenUtil.generateTokenWithPinAndEmail(user);
                String emailBody = AppConstant.EMAIL_VERIFICATION_BODY + token;
                this.emailService.sendEmail(user.getEmail(), AppConstant.EMAIL_VERIFICATION_SUBJECT, emailBody);
                user.setPasswordVerifiedCode(emailBody);
//                return user;
            } else if (this.userRepository.findByEmailAndStatusSeq(user.getEmail(), Status.PENDING.getStatusSeq()).getEmail().equals(user.getEmail())) {
                user = this.userRepository.findByEmailAndStatusSeq(user.getEmail(), Status.PENDING.getStatusSeq());
                this.userRepository.save(user);
                String token = this.jwtTokenUtil.generateTokenWithPinAndEmail(user);
                String emailBody = AppConstant.EMAIL_VERIFICATION_BODY + token;
                this.emailService.sendEmail(user.getEmail(), AppConstant.EMAIL_VERIFICATION_SUBJECT, emailBody);
                user.setPasswordVerifiedCode(emailBody);
//                return user;
            }
            return user;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public User emailVerificationFromRegisteredUser(String token) {
        LOGGER.info("Enter emailVerificationFromRegisteredUser() in UserService.");
        List<String> UserEmailAndPinFromToken = this.jwtTokenUtil.getUserEmailAndPinFromToken(token);
        String generatedPinOfToken = UserEmailAndPinFromToken.get(0);
        String userEmailOfToken = UserEmailAndPinFromToken.get(1);
        User user = null;
        try {
            user = this.userRepository.findByEmailAndStatusSeq(userEmailOfToken, Status.PENDING.getStatusSeq());
            if (user != null) {
                if (this.utilService.matchingEmailGeneratedPinWithActual(userEmailOfToken, generatedPinOfToken) == true && user.getStatusSeq().equals(Status.PENDING.getStatusSeq())) {
                    user.setIsEmailVerified(true);
                    user.setStatusSeq(Status.APPROVED.getStatusSeq());
                    user.setRoleSeq(Role.VENDOR.getRoleSeq());
                    this.userRepository.saveAndFlush(user);
                    user = this.userRepository.findByEmailAndStatusSeq(userEmailOfToken, Status.APPROVED.getStatusSeq());
                } else {
                    throw new BadResponseException("The Request Cannot Be Fulfilled Due To Bad Syntax Exception.");
                }
            } else {
                throw new BadResponseException("Resource Not Found Exception.");
            }
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
        return user;
    }

    public User loadUserByLogin(SignUpDto signUpDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = this.userRepository.findByEmailAndStatusSeq(signUpDto.getEmail(), Status.APPROVED.getStatusSeq());
        if (user.getStatusSeq().equals(Status.APPROVED.getStatusSeq())) {
            String hashedPassword = user.getPassword();
            if (passwordEncoder.matches(signUpDto.getPassword(), hashedPassword)) {
                return user;
            } else {
                throw new UnauthorizedException("Authentication Failed. Your Password incorrect");
            }
        } else {
            throw new UnauthorizedException("Authentication Failed. Your Username incorrect");
        }
    }

    public String sendEmailForPasswordResetRequest(String email) {
        LOGGER.info("Enter sendEmailForPasswordResetRequest() in UserService.");
        String response = null;
        try {
            User user = this.userRepository.findByEmailAndStatusSeq(email, Status.APPROVED.getStatusSeq());
            if (user != null) {
                user.setPasswordVerifiedCode(UtilService.generateRandomString());
                user.setIsPasswordReset(false);
                this.userRepository.saveAndFlush(user);
                String token = this.jwtTokenUtil.generateTokenWithPinAndEmail(user);
                String emailBody = AppConstant.PASSWORD_RESET_VERIFICATION_BODY + token;
                this.emailService.sendEmail(user.getEmail(), AppConstant.PASSWORD_RESET_VERIFICATION_SUBJECT, emailBody);
                response = "Email Send Successfully " + emailBody;
            } else {
                throw new BadResponseException("Resource Not Found Exception.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public User validatePasswordResetRequestAndSaveNewPassword(String token, PasswordResetDto passwordResetDto) {
        LOGGER.info("Enter validatePasswordResetRequestAndSaveNewPassword() in UserService.");
        User user = new User();
        try {
            List<String> UserEmailAndPinFromToken = this.jwtTokenUtil.getUserEmailAndPinFromToken(token);
            String generatedPinOfToken = UserEmailAndPinFromToken.get(0);
            String userEmailOfToken = UserEmailAndPinFromToken.get(1);
            user = this.userRepository.findByEmailAndStatusSeq(userEmailOfToken, Status.APPROVED.getStatusSeq());
            if (user != null && passwordResetDto.getNewPassword().equals(passwordResetDto.getConfirmPassword()) && user.getIsPasswordReset() == false) {
                if (utilService.matchingEmailGeneratedPinWithActual(userEmailOfToken, generatedPinOfToken) == true) {
                    user.setIsPasswordReset(true);
                    this.userRepository.saveAndFlush(user);
                    user = this.userRepository.findByUserSeqAndStatusSeq(user.getUserSeq(), Status.APPROVED.getStatusSeq());
                }
            } else {
                throw new BadResponseException("Resource Not Found Exception.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
