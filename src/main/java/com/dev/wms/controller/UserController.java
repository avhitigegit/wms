package com.dev.wms.controller;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.common.enums.Status;
import com.dev.wms.dto.*;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.model.User;
import com.dev.wms.repository.UserRepository;
import com.dev.wms.service.ContactService;
import com.dev.wms.service.DescriptionService;
import com.dev.wms.service.ImageService;
import com.dev.wms.service.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final ContactService contactService;
    private final DescriptionService descriptionService;
    private final ImageService imageService;
    private final LocationService locationService;


    @Autowired
    public UserController(UserRepository userRepository,
                          LocationService locationService,
                          ContactService contactService,
                          DescriptionService descriptionService,
                          ImageService imageService) {
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.contactService = contactService;
        this.descriptionService = descriptionService;
        this.imageService = imageService;
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        LOGGER.info("Enter createUser() in UserController.");
        User user = new User();
        User originUser = this.userRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getUserSeq(), Status.APPROVED.getStatusSeq());
        userDto = User.setUserInRegistration(originUser, userDto);
        if (userDto != null) {
            List<ContactDto> contactDtoList = userDto.getContactDtoList();
            List<DescriptionDto> descriptionDtoList = userDto.getDescriptionDtoList();
            List<ImageDto> imageDtoList = userDto.getImageDtoList();
            List<LocationDto> locationDtoList = userDto.getLocationDtoList();

            if (contactDtoList != null) {
                this.contactService.saveAddressList(contactDtoList);
            }
            if (descriptionDtoList != null) {
                this.descriptionService.saveAddressList(descriptionDtoList);
            }
            if (imageDtoList != null) {
//                this.imageService.saveAddressList(imageDtoList);
            }
            if (locationDtoList != null) {
                this.locationService.saveAddressList(locationDtoList);
            }
//            user = userService.profileCreateForRegisteredUser(user);
            BeanUtils.copyProperties(user, userDto);
            return ResponseEntity.ok().body(userDto);
        } else {
            throw new BadRequestException("The Request Cannot Be Fulfilled Due To Bad Syntax Exception.");
        }
    }

}
