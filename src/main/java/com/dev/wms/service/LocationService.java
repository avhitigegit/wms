package com.dev.wms.service;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.common.enums.Status;
import com.dev.wms.dto.LocationDto;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.exception.BadResponseException;
import com.dev.wms.model.Location;
import com.dev.wms.repository.LocationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {
    private static final Logger LOGGER = LogManager.getLogger(LocationService.class);

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void saveLocation(LocationDto locationDto) {
        LOGGER.info("Enter saveLocation() in LocationService. " + CurrentUser.getUser().getEmail());
        try {
            if (locationDto != null) {
                Location location = new Location();
                BeanUtils.copyProperties(locationDto, location);
                Location.initFrom(location);
                locationRepository.save(location);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void saveLocationList(List<LocationDto> locationDtoList) {
        LOGGER.info("Enter saveLocationList() in LocationService. " + CurrentUser.getUser().getEmail());
        try {
            if (locationDtoList != null) {
                for (LocationDto locationDto : locationDtoList) {
                    Location location = new Location();
                    BeanUtils.copyProperties(locationDto, location);
                    Location.initFrom(location);
                    locationRepository.save(location);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<LocationDto> getMyLocationList() {
        LOGGER.info("Enter getMyLocationList() in LocationService. " + CurrentUser.getUser().getEmail());
        List<LocationDto> locationDtoList = new ArrayList<>();
        try {
            List<Location> locationList = locationRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getUserSeq(), Status.APPROVED.getStatusSeq());
            if (locationList != null) {
                for (Location location : locationList) {
                    LocationDto locationDto = new LocationDto();
                    BeanUtils.copyProperties(location, locationDto);
                    locationDtoList.add(locationDto);
                }
            }
            return locationDtoList;
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
    }

    public LocationDto updateLocation(LocationDto tempLocationDto) {
        LOGGER.info("Enter updateLocation() in LocationService. " + CurrentUser.getUser().getEmail());
        try {
            if (tempLocationDto.getLocationSeq() != null) {
                Location location = this.locationRepository.findByLocationSeqAndStatusSeq(tempLocationDto.getLocationSeq(), Status.APPROVED.getStatusSeq());
                if (location.getLocationSeq() != null) {
                    location.setZipCode(tempLocationDto.getZipCode());
                    location.setStreet_01(tempLocationDto.getStreet_01());
                    location.setStreet_02(tempLocationDto.getStreet_02());
                    location.setStreet_03(tempLocationDto.getStreet_03());
                    location.setCity(tempLocationDto.getCity());
                    location.setProvince(tempLocationDto.getProvince());
                    location.setLatitudes(tempLocationDto.getLatitudes());
                    location.setLongitudes(tempLocationDto.getLongitudes());
                    location.setStatusSeq(tempLocationDto.getStatusSeq());
                    location.setUserSeq(CurrentUser.getUser().getUserSeq());
                    this.locationRepository.save(location);
                }
            }
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
        return this.getSingleLocationByID(tempLocationDto.getLocationSeq());
    }

    public void deactivateLocation(LocationDto locationDto) {
        LOGGER.info("Enter deactivateLocation() in LocationService. " + CurrentUser.getUser().getEmail());
        try {
            Location location = this.locationRepository.findByLocationSeqAndStatusSeq(locationDto.getLocationSeq(), Status.APPROVED.getStatusSeq());
            if (location != null) {
                location.setStatusSeq(Status.DELETED.getStatusSeq());
                location.setUserSeq(CurrentUser.getUser().getUserSeq());
                this.locationRepository.save(location);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public LocationDto getSingleLocationByID(String locationSeq) {
        LOGGER.info("Enter getSingleLocation() in LocationService. " + CurrentUser.getUser().getEmail());
        LocationDto locationDto = new LocationDto();
        try {
            Location location = this.locationRepository.findByLocationSeqAndStatusSeq(locationSeq, Status.APPROVED.getStatusSeq());
            if (location != null) {
                BeanUtils.copyProperties(location, locationDto);
            }
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
        return locationDto;
    }

    public List<LocationDto> getAllLocationList() {
        LOGGER.info("Enter getAllLocationList() in LocationService. " + CurrentUser.getUser().getEmail());
        List<LocationDto> locationDtoList = new ArrayList<>();
        try {
            List<Location> locationList = this.locationRepository.findAll();
            if (locationList != null) {
                for (Location location : locationList) {
                    LocationDto locationDto = new LocationDto();
                    BeanUtils.copyProperties(location, locationDto);
                    locationDtoList.add(locationDto);
                }
            }
            return locationDtoList;
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
    }

}
