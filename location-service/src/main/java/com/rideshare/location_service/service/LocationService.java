package com.rideshare.location_service.service;


import com.rideshare.location_service.dto.DriverLocationRequest;
import com.rideshare.location_service.dto.NearByDriverResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

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

    public List<NearByDriverResponse>getNearByDriverLocation(
            double longitude,
            double latitude,
            double radiusInKm) {

        log.info("Finding drivers near lat: {} and long:{} within {} km",
                latitude, longitude,radiusInKm);

        Circle searchArea = new Circle(
                new Point(longitude,latitude),
                new Distance(radiusInKm, RedisGeoCommands.DistanceUnit.KILOMETERS)
                );

       GeoResults<RedisGeoCommands.GeoLocation<String>> result =
               redisTemplate.opsForGeo().radius(
                       Drivers_GEO_KEY,
                       searchArea,
                       RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                               .includeCoordinates()
                               .includeDistance()
                               .sortAscending()
                               .limit(10));


       List<NearByDriverResponse> nearByDriverResponseList = new ArrayList<>();
       if(result!=null) {
         result.getContent().forEach(result-> {
             RedisGeoCommands.GeoLocation<String> location = result.getContent();
             nearByDriverResponseList.add(new NearByDriverResponse(
                location.getName(), location.getPoint().getX(),
                location.getPoint().getY(), result.getDistance().getValue()
             ));
         });
       }
       log.info("Found {} drivers nearby", nearByDriverResponseList.size());
       return nearByDriverResponseList;
    }

    /*
     *  Remove the driver when they go offline
     *  Map to Redis ZERM Command
     * */

    public void removeDriver(String  driverId) {
        log.info("Removing driver id {}", driverId);
        redisTemplate.opsForGeo().remove(Drivers_GEO_KEY,driverId);
    }

}
