package com.dev.wms.service;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.common.enums.Status;
import com.dev.wms.dto.ImageDto;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.model.Image;
import com.dev.wms.repository.ImageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    private static final Logger LOGGER = LogManager.getLogger(LocationService.class);

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    //Save my address
    public void saveAddress(ImageDto imageDto) {
        LOGGER.info("Enter saveAddress() in AddressService.");
        try {
            if (imageDto != null) {
                Image image = new Image();
                BeanUtils.copyProperties(imageDto, image);
                Image.initFrom(image);
                imageRepository.save(image);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //Save my address list
    public void saveAddressList(List<ImageDto> imageDtoList) {
        LOGGER.info("Enter saveAddressList() in AddressService.");
        try {
            if (imageDtoList != null) {
                for (ImageDto imageDto : imageDtoList) {
                    Image image = new Image();
                    BeanUtils.copyProperties(imageDto, image);
                    Image.initFrom(image);
                    imageRepository.save(image);
                }
            }
        } catch (BeansException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //Get my all addresses
    public List<ImageDto> getMyAddresses() {
        LOGGER.info("Enter getMyAddresses() in AddressService.");
        List<ImageDto> imageDtoList = new ArrayList<>();
        try {
            List<Image> imageList = imageRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getUserSeq(), Status.APPROVED.getStatusSeq());
            if (imageList != null) {
                for (Image image : imageList) {
                    ImageDto imageDto = new ImageDto();
                    BeanUtils.copyProperties(image, imageDto);
                    imageDtoList.add(imageDto);
                }
            }
            return imageDtoList;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //Update my address record
    public ImageDto updateAddress(ImageDto tempImageDto) {
        LOGGER.info("Enter updateAddress() in AddressService.");
        try {
            if (tempImageDto.getImageSeq() != null) {
                Image image = this.imageRepository.findByImageSeqAndStatusSeq(tempImageDto.getImageSeq(), Status.APPROVED.getStatusSeq());
                if (image.getImageSeq() != null) {
                    image.setName(tempImageDto.getName());
                    image.setUrl(tempImageDto.getUrl());
                    image.setImageTypeSeq(tempImageDto.getImageTypeSeq());
                    image.setStatusSeq(tempImageDto.getStatusSeq());
                    image.setUserSeq(CurrentUser.getUser().getUserSeq());
                    this.imageRepository.save(image);
                }
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
        return this.getSingleAddress(tempImageDto.getImageSeq());
    }

    //Delete my address record
    public void deactivateAddress(ImageDto imageDto) {
        LOGGER.info("Enter deactivateAddress() in AddressService.");
        try {
            Image image = this.imageRepository.findByImageSeqAndStatusSeq(imageDto.getImageSeq(), Status.APPROVED.getStatusSeq());
            if (image != null) {
                image.setStatusSeq(Status.DELETED.getStatusSeq());
                image.setUserSeq(CurrentUser.getUser().getUserSeq());
                this.imageRepository.save(image);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //Get single address record
    public ImageDto getSingleAddress(String imageSeq) {
        LOGGER.info("Enter getSingleAddress() in AddressService.");
        ImageDto imageDto = new ImageDto();
        try {
            Image image = this.imageRepository.findByImageSeqAndStatusSeq(imageSeq, Status.APPROVED.getStatusSeq());
            if (image != null) {
                BeanUtils.copyProperties(image, imageDto);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return imageDto;
    }
}
