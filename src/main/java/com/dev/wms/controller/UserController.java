package com.dev.wms.controller;

import com.dev.wms.dto.UserDto;
import com.dev.wms.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        LOGGER.info("Enter createUser() in UserController.");
        return ResponseEntity.ok().body(this.userService.createUser(userDto));
    }

    @DeleteMapping
    public ResponseEntity<String> deactivateProfile() {
        LOGGER.info("Enter deactivateProfile() in UserController.");
        return ResponseEntity.ok().body(this.userService.deactivateProfile());
    }

    @GetMapping
    public ResponseEntity<UserDto> getProfile() {
        LOGGER.info("Enter getProfile() in UserController.");
        return ResponseEntity.ok().body(userService.getProfile());
    }

}
