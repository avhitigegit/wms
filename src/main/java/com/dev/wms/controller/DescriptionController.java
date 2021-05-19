package com.dev.wms.controller;

import com.dev.wms.dto.DescriptionDto;
import com.dev.wms.service.DescriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("description")
public class DescriptionController {
    private static final Logger LOGGER = LogManager.getLogger(DescriptionController.class);

    private final DescriptionService descriptionService;

    public DescriptionController(DescriptionService descriptionService) {
        this.descriptionService = descriptionService;
    }

    @PostMapping()
    public ResponseEntity createDescription(@RequestBody DescriptionDto descriptionDto) {
        LOGGER.info("Enter createDescription() in DescriptionController.");
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(this.descriptionService.saveDescription(descriptionDto), HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseEntity);
    }

    @GetMapping
    public ResponseEntity<List<DescriptionDto>> getMyDescriptionList() {
        LOGGER.info("Enter getMyDescriptionList() in DescriptionController.");
        return ResponseEntity.ok().body(this.descriptionService.getMyDescriptionList());
    }

    @GetMapping("{descriptionSeq}")
    public ResponseEntity<DescriptionDto> getDescription(@PathVariable("descriptionSeq") String descriptionSeq) {
        LOGGER.info("Enter getDescription() in DescriptionController.");
        return ResponseEntity.ok().body(this.descriptionService.getSingleDescriptionByID(descriptionSeq));
    }

    @PutMapping()
    public ResponseEntity updateDescription(@RequestBody DescriptionDto tempDescriptionDto) {
        LOGGER.info("Enter updateDescription() in DescriptionController.");
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(this.descriptionService.updateDescription(tempDescriptionDto), HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseEntity);
    }

    @DeleteMapping
    public ResponseEntity deactivateDescription(DescriptionDto descriptionDto) {
        LOGGER.info("Enter deactivateDescription() in DescriptionController.");
        return ResponseEntity.ok().body(this.descriptionService.deactivateDescription(descriptionDto));
    }

    //Admin
    @GetMapping("/all-descriptions")
    public ResponseEntity<List<DescriptionDto>> getAllDescriptions() {
        LOGGER.info("Enter getAllDescriptions() in DescriptionController.");
        return ResponseEntity.ok().body(this.descriptionService.getAllDescriptionList());
    }

    @GetMapping("/active-descriptions")
    public ResponseEntity<List<DescriptionDto>> getActiveDescriptionList() {
        LOGGER.info("Enter getActiveDescriptionList() in DescriptionController.");
        return ResponseEntity.ok().body(this.descriptionService.getAllDescriptionList());
    }

    @GetMapping("/de-active-descriptions")
    public ResponseEntity<List<DescriptionDto>> getDeActivateDescriptionList() {
        LOGGER.info("Enter getDeActivateDescriptionList() in DescriptionController.");
        return ResponseEntity.ok().body(this.descriptionService.getAllDescriptionList());
    }
}
