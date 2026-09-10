package com.rideshare.location_service.service;


import com.rideshare.location_service.dto.DriverLocationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.geo.Point;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final RedisTemplate<String,String> redisTemplate;

    // Redis key for all driver location
    private static final String Drivers_GEO_KEY = "drivers:location";

    /*
    *  Update Drivers Location in Redis
    *  Called every 3 seconds by driver's phone
    *  Maps to Redis GEOADD commands to add and update the drivers locations
    * */

    public void updateDriverLocation(DriverLocationRequest driverLocationRequest) {
        log.info("Updating Driver Location for driver id {}", driverLocationRequest.getDriverId());

        // Longitude first, Latitude second - GeoSpatial Standard
        Point driverLocation = new Point(
                driverLocationRequest.getLongitude(),
                driverLocationRequest.getLatitude()
        );

        redisTemplate.opsForGeo().add(
                Drivers_GEO_KEY,
                driverLocation,
                driverLocationRequest.getDriverId()
        );
    }

}
