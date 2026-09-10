package com.rideshare.ride_service.service;

import com.rideshare.ride_service.dto.RideRequest;
import com.rideshare.ride_service.dto.RideResponse;
import com.rideshare.ride_service.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;

    public RideResponse requestRide( RideRequest rideRequest) {
    }

    public RideResponse getRideById(String rideId) {
    }

    public List<RideResponse> getRidesByRider(String riderId) {
    }

    public RideResponse startRide(String rideId) {
    }

    public RideResponse completeRide(String rideId) {
    }

    public RideResponse cancelRide(String rideId) {
    }
}
