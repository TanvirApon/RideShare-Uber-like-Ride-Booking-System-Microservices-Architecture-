package com.rideshare.matching_service.service;

import lombok.RequiredArgsConstructor;
import com.rideshare.matching_service.event.RideRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class RideEventConsumer {

    private final MatchingService matchingService;

    @KafkaListener(
            topics = "ride.requested",
            groupId = "matching-service-group"
    )
    public void consumeRideRequestedEvent(RideRequestEvent event){
        try{
            matchingService.matchDriverForRide(event);
        }
        catch (Exception e){
            log.error("Error processing ride request: {} - {}",
                    event.getRideId(), e.getMessage());
        }
    }
}
