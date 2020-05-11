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

    //Save my address
    public void saveAddress(LocationDto locationDto) {
        LOGGER.info("Enter saveAddress() in AddressService.");
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

    //Save my address list
    public void saveAddressList(List<LocationDto> locationDtoList) {
        LOGGER.info("Enter saveAddressList() in AddressService.");
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

    //Get my all addresses
    public List<LocationDto> getMyAddresses() {
        LOGGER.info("Enter getMyAddresses() in AddressService.");
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

    //Update my address record
    public LocationDto updateAddress(LocationDto tempLocationDto) {
        LOGGER.info("Enter updateAddress() in AddressService.");
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
        return this.getSingleAddress(tempLocationDto.getLocationSeq());
    }

    //Delete my address record
    public void deactivateAddress(LocationDto locationDto) {
        LOGGER.info("Enter deactivateAddress() in AddressService.");
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

    //Get single address record
    public LocationDto getSingleAddress(String locationSeq) {
        LOGGER.info("Enter getSingleAddress() in AddressService.");
        LocationDto locationDto = new LocationDto();
        try {
            Location location = this.locationRepository.findByLocationSeqAndStatusSeq(locationSeq, Status.APPROVED.getStatusSeq());
            if (location != null) {
                BeanUtils.copyProperties(location, locationDto);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return locationDto;
    }

}
