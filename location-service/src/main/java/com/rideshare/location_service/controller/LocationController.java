package com.rideshare.location_service.controller;


import com.rideshare.location_service.dto.DriverLocationRequest;
import com.rideshare.location_service.dto.NearByDriverResponse;
import com.rideshare.location_service.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@Slf4j
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;


    @PostMapping("/drivers/update")
    public ResponseEntity<String> updateDriverLocation(
            @RequestBody DriverLocationRequest) {

        locationService.updateDriverLocation(DriverLocationRequest);
        return ResponseEntity.ok("Driver Location Updated");
    }

    @GetMapping("/drivers/nearby")
    public ResponseEntity<List<NearByDriverResponse>> getNearByDriverLocation(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam(defaultValue = "0.5") double radius) {

        return ResponseEntity.ok(locationService.getNearByDriver(longitude, latitude, radius));
    }


    @DeleteMapping("/drivers/{driverId}")
    public ResponseEntity<String>removeDriverById(@PathVariable String driverId) {
        locationService.removeDriver(driverId);
        return ResponseEntity.ok("Driver Removed Successfully");
    }


}


