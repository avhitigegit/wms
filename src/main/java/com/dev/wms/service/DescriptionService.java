package com.dev.wms.service;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.common.enums.Status;
import com.dev.wms.dto.DescriptionDto;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.model.Description;
import com.dev.wms.repository.DescriptionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DescriptionService {
    private static final Logger LOGGER = LogManager.getLogger(LocationService.class);

    private final DescriptionRepository descriptionRepository;

    @Autowired
    public DescriptionService(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    //Save my address
    public void saveAddress(DescriptionDto descriptionDto) {
        LOGGER.info("Enter saveAddress() in AddressService.");
        try {
            if (descriptionDto != null) {
                Description description = new Description();
                BeanUtils.copyProperties(descriptionDto, description);
                Description.initFrom(description);
                descriptionRepository.save(description);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //Save my address list
    public void saveAddressList(List<DescriptionDto> locationDtoList) {
        LOGGER.info("Enter saveAddressList() in AddressService.");
        try {
            if (locationDtoList != null) {
                for (DescriptionDto descriptionDto : locationDtoList) {
                    Description description = new Description();
                    BeanUtils.copyProperties(descriptionDto, description);
                    Description.initFrom(description);
                    descriptionRepository.save(description);
                }
            }
        } catch (BeansException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //Get my all addresses
    public List<DescriptionDto> getMyAddresses() {
        LOGGER.info("Enter getMyAddresses() in AddressService.");
        List<DescriptionDto> locationDtoList = new ArrayList<>();
        try {
            List<Description> locationList = descriptionRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getUserSeq(), Status.APPROVED.getStatusSeq());
            if (locationList != null) {
                for (Description description : locationList) {
                    DescriptionDto descriptionDto = new DescriptionDto();
                    BeanUtils.copyProperties(description, descriptionDto);
                    locationDtoList.add(descriptionDto);
                }
            }
            return locationDtoList;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //Update my address record
    public DescriptionDto updateAddress(DescriptionDto tempDescriptionDto) {
        LOGGER.info("Enter updateAddress() in AddressService.");
        try {
            if (tempDescriptionDto.getDescriptionSeq() != null) {
                Description description = this.descriptionRepository.findByDescriptionSeqAndStatusSeq(tempDescriptionDto.getDescriptionSeq(), Status.APPROVED.getStatusSeq());
                if (description.getDescriptionSeq() != null) {
                    description.setWebsite(tempDescriptionDto.getWebsite());
                    description.setFacebook(tempDescriptionDto.getFacebook());
                    description.setLinkedin(tempDescriptionDto.getLinkedin());
                    description.setDescription_1(tempDescriptionDto.getDescription_1());
                    description.setDescription_2(tempDescriptionDto.getDescription_2());
                    description.setStatusSeq(tempDescriptionDto.getStatusSeq());
                    description.setUserSeq(CurrentUser.getUser().getUserSeq());
                    this.descriptionRepository.save(description);
                }
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
        return this.getSingleAddress(tempDescriptionDto.getDescriptionSeq());
    }

    //Delete my address record
    public void deactivateAddress(DescriptionDto descriptionDto) {
        LOGGER.info("Enter deactivateAddress() in AddressService.");
        try {
            Description description = this.descriptionRepository.findByDescriptionSeqAndStatusSeq(descriptionDto.getDescriptionSeq(), Status.APPROVED.getStatusSeq());
            if (description != null) {
                description.setStatusSeq(Status.DELETED.getStatusSeq());
                description.setUserSeq(CurrentUser.getUser().getUserSeq());
                this.descriptionRepository.save(description);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //Get single address record
    public DescriptionDto getSingleAddress(String descriptionSeq) {
        LOGGER.info("Enter getSingleAddress() in AddressService.");
        DescriptionDto descriptionDto = new DescriptionDto();
        try {
            Description description = this.descriptionRepository.findByDescriptionSeqAndStatusSeq(descriptionSeq, Status.APPROVED.getStatusSeq());
            if (description != null) {
                BeanUtils.copyProperties(description, descriptionDto);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return descriptionDto;
    }
}
