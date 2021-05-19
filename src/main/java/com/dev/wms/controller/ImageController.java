package com.dev.wms.controller;

import com.dev.wms.dto.ImageDto;
import com.dev.wms.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("image")
public class ImageController {
    private static final Logger LOGGER = LogManager.getLogger(ImageController.class);

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
    public ResponseEntity createImage(@RequestBody ImageDto imageDto) {
        LOGGER.info("Enter createImage() in ImageController.");
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(this.imageService.saveImage(imageDto), HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseEntity);
    }

    @GetMapping
    public ResponseEntity<List<ImageDto>> getMyImageList() {
        LOGGER.info("Enter getMyImageList() in ImageController.");
        return ResponseEntity.ok().body(this.imageService.getMyImageList());
    }

    @GetMapping("{imageSeq}")
    public ResponseEntity<ImageDto> getImage(@PathVariable("imageSeq") String imageSeq) {
        LOGGER.info("Enter getImage() in ImageController.");
        return ResponseEntity.ok().body(this.imageService.getSingleImageByID(imageSeq));
    }

    @PutMapping()
    public ResponseEntity updateImage(@RequestBody ImageDto tempImageDto) {
        LOGGER.info("Enter updateImage() in ImageController.");
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(this.imageService.updateImage(tempImageDto), HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseEntity);
    }

    @DeleteMapping
    public ResponseEntity deactivateImage(ImageDto imageDto) {
        LOGGER.info("Enter deactivateImage() in ImageController.");
        return ResponseEntity.ok().body(this.imageService.deactivateImage(imageDto));
    }

    //Admin
    @GetMapping("/all-images")
    public ResponseEntity<List<ImageDto>> getAllImages() {
        LOGGER.info("Enter getAllImages() in ImageController.");
        return ResponseEntity.ok().body(this.imageService.getAllImageList());
    }

    @GetMapping("/active-images")
    public ResponseEntity<List<ImageDto>> getActiveImageList() {
        LOGGER.info("Enter getActiveImageList() in ImageController.");
        return ResponseEntity.ok().body(this.imageService.getAllImageList());
    }

    @GetMapping("/de-active-images")
    public ResponseEntity<List<ImageDto>> getDeActivateImageList() {
        LOGGER.info("Enter getDeActivateImageList() in ImageController.");
        return ResponseEntity.ok().body(this.imageService.getAllImageList());
    }
}
