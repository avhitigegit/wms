package com.dev.wms.controller;

import com.dev.wms.dto.LocationDto;
import com.dev.wms.service.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("location")
public class LocationController {
    private static final Logger LOGGER = LogManager.getLogger(LocationController.class);

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping()
    public ResponseEntity createLocation(@RequestBody LocationDto locationDto) {
        LOGGER.info("Enter createLocation() in LocationController.");
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(this.locationService.saveLocation(locationDto), HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseEntity);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getMyLocationList() {
        LOGGER.info("Enter getMyLocationList() in LocationController.");
        return ResponseEntity.ok().body(this.locationService.getMyLocationList());
    }

    @GetMapping("{locationSeq}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable("locationSeq") String locationSeq) {
        LOGGER.info("Enter getLocation() in LocationController.");
        return ResponseEntity.ok().body(this.locationService.getSingleLocationByID(locationSeq));
    }

    @PutMapping()
    public ResponseEntity updateLocation(@RequestBody LocationDto tempLocationDto) {
        LOGGER.info("Enter updateLocation() in LocationController.");
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(this.locationService.updateLocation(tempLocationDto), HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseEntity);
    }

    @DeleteMapping
    public ResponseEntity deactivateLocation(LocationDto locationDto) {
        LOGGER.info("Enter deactivateLocation() in LocationController.");
        return ResponseEntity.ok().body(this.locationService.deactivateLocation(locationDto));
    }

    //Admin
    @GetMapping("/all-locations")
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        LOGGER.info("Enter getAllLocations() in LocationController.");
        return ResponseEntity.ok().body(this.locationService.getAllLocationList());
    }

    @GetMapping("/active-locations")
    public ResponseEntity<List<LocationDto>> getActiveLocationList() {
        LOGGER.info("Enter getActiveLocationList() in LocationController.");
        return ResponseEntity.ok().body(this.locationService.getAllLocationList());
    }

    @GetMapping("/de-active-locations")
    public ResponseEntity<List<LocationDto>> getDeActivateLocationList() {
        LOGGER.info("Enter getDeActivateLocationList() in LocationController.");
        return ResponseEntity.ok().body(this.locationService.getAllLocationList());
    }
}
