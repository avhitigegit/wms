package com.dev.wms.service;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.common.enums.Status;
import com.dev.wms.dto.ImageDto;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.exception.BadResponseException;
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
    private static final Logger LOGGER = LogManager.getLogger(ImageService.class);

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(ImageDto imageDto) {
        LOGGER.info("Enter saveImage() in ImageService. " + CurrentUser.getUser().getEmail());
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

    public void saveImageList(List<ImageDto> imageDtoList) {
        LOGGER.info("Enter saveImageList() in ImageService. " + CurrentUser.getUser().getEmail());
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

    public List<ImageDto> getMyImageList() {
        LOGGER.info("Enter getMyImageList() in ImageService. " + CurrentUser.getUser().getEmail());
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
            throw new BadResponseException(e.getMessage());
        }
    }

    public ImageDto updateImage(ImageDto tempImageDto) {
        LOGGER.info("Enter updateImage() in ImageService. " + CurrentUser.getUser().getEmail());
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
        return this.getSingleImageByID(tempImageDto.getImageSeq());
    }

    public void deactivateImage(ImageDto imageDto) {
        LOGGER.info("Enter deactivateImage() in ImageService. " + CurrentUser.getUser().getEmail());
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

    public ImageDto getSingleImageByID(String imageSeq) {
        LOGGER.info("Enter getSingleImage() in ImageService. " + CurrentUser.getUser().getEmail());
        ImageDto imageDto = new ImageDto();
        try {
            Image image = this.imageRepository.findByImageSeqAndStatusSeq(imageSeq, Status.APPROVED.getStatusSeq());
            if (image != null) {
                BeanUtils.copyProperties(image, imageDto);
            }
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
        return imageDto;
    }

    public List<ImageDto> getAllImageList() {
        LOGGER.info("Enter getAllImageList() in ImageService. " + CurrentUser.getUser().getEmail());
        List<ImageDto> imageDtoList = new ArrayList<>();
        try {
            List<Image> imageList = this.imageRepository.findAll();
            if (imageList != null) {
                for (Image image : imageList) {
                    ImageDto imageDto = new ImageDto();
                    BeanUtils.copyProperties(image, imageDto);
                    imageDtoList.add(imageDto);
                }
            }
            return imageDtoList;
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
    }
}
