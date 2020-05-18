package com.dev.wms.service;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.common.enums.Status;
import com.dev.wms.dto.*;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.exception.BadResponseException;
import com.dev.wms.model.User;
import com.dev.wms.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ContactService contactService;
    private final DescriptionService descriptionService;
    private final ImageService imageService;
    private final LocationService locationService;

    @Autowired
    public UserService(UserRepository userRepository,
                       ContactService contactService,
                       DescriptionService descriptionService,
                       ImageService imageService,
                       LocationService locationService) {
        this.userRepository = userRepository;
        this.contactService = contactService;
        this.descriptionService = descriptionService;
        this.imageService = imageService;
        this.locationService = locationService;
    }

    public UserDto createUser(UserDto userDto) {
        LOGGER.info("Enter createUser() in UserService. " + CurrentUser.getUser().getEmail());
        try {
            User user = new User();
            User dbUser = this.userRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getUserSeq(), Status.APPROVED.getStatusSeq());
            if (dbUser != null) {
                userDto = User.setUserInRegistration(dbUser, userDto);
            }
            if (userDto != null) {
                List<ContactDto> contactDtoList = userDto.getContactDtoList();
                List<DescriptionDto> descriptionDtoList = userDto.getDescriptionDtoList();
                List<ImageDto> imageDtoList = userDto.getImageDtoList();
                List<LocationDto> locationDtoList = userDto.getLocationDtoList();

                if (contactDtoList != null) {
                    this.contactService.saveContactList(contactDtoList);
                }
                if (descriptionDtoList != null) {
                    this.descriptionService.saveDescriptionList(descriptionDtoList);
                }
                if (imageDtoList != null) {
                    this.imageService.saveImageList(imageDtoList);
                }
                if (locationDtoList != null) {
                    this.locationService.saveLocationList(locationDtoList);
                }
                BeanUtils.copyProperties(userDto, user);
                this.userRepository.saveAndFlush(user);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return getMyProfile();
    }

    public UserDto getMyProfile() {
        LOGGER.info("Enter getMyProfile() in UserService. " + CurrentUser.getUser().getEmail());
        UserDto userDto = new UserDto();
        try {
            User user = userRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getUserSeq(), Status.APPROVED.getStatusSeq());
            BeanUtils.copyProperties(user, userDto);
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
        return userDto;
    }

    public String deactivateProfile() {
        LOGGER.info("Enter deactivateProfile() in UserService. " + CurrentUser.getUser().getEmail());
        String deactivateStatus = null;
        try {
            User user = this.userRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getUserSeq(), Status.APPROVED.getStatusSeq());
            if (user.getEmail() != null) {
                deactivateStatus = "Deactivate User Successfully.";
                this.userRepository.deleteById(user.getUserSeq());
            }
        } catch (Exception e) {
            throw new BadResponseException("Resource Not Found Exception.");
        }
        return deactivateStatus;
    }

    public UserDto getProfile() {
        LOGGER.info("Enter getProfile() in UserService. " + CurrentUser.getUser().getEmail());
        UserDto userDto = new UserDto();
        try {
            userDto = this.getMyProfile();
            List<ContactDto> contactDtoList = contactService.getContactList();
            List<DescriptionDto> descriptionDtoList = descriptionService.getMyDescriptionList();
            List<ImageDto> imageDtoList = imageService.getMyImageList();
            List<LocationDto> locationDtoList = locationService.getMyLocationList();

            userDto.setContactDtoList(contactDtoList);
            userDto.setDescriptionDtoList(descriptionDtoList);
            userDto.setImageDtoList(imageDtoList);
            userDto.setLocationDtoList(locationDtoList);

        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
        return userDto;
    }
}
