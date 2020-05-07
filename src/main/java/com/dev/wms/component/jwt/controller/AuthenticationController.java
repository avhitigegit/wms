package com.dev.wms.component.jwt.controller;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.component.jwt.model.SignUpDto;
import com.dev.wms.component.jwt.service.AuthenticationService;
import com.dev.wms.dto.UserDto;
import com.dev.wms.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //Generate a new user and send email
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDto> registerUserAndSendEmail(@RequestBody SignUpDto signUpDto) {
        LOGGER.info("Enter registerUserAndSendEmail() in AuthenticationController." + signUpDto.getEmail());
        User user = new User();
        UserDto userDto = new UserDto();
        SignUpDto.passwordvalidation(signUpDto.getPassword());
        BeanUtils.copyProperties(signUpDto, user);
        user = authenticationService.registerUserAndSendEmail(user);
        BeanUtils.copyProperties(user, userDto);
        return ResponseEntity.ok().body(userDto);
    }

    //Get the verification request from generated user
    @RequestMapping(value = "/verification", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> emailVerificationFromRegisteredUser(@RequestBody String confirmationToken) {
        LOGGER.info("Enter emailVerificationFromRegisteredUser() in AuthenticationController.");
        UserDto userDto = new UserDto();
        User user = authenticationService.emailVerificationFromRegisteredUser(confirmationToken);
        CurrentUser.setUser(user);
        BeanUtils.copyProperties(user, userDto);
        return ResponseEntity.ok().body(userDto);
    }
}
