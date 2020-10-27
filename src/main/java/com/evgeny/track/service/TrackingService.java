package com.evgeny.track.service;

import com.evgeny.track.entity.TrackingEntity;
import com.evgeny.track.repository.TrackingRepositiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackingService {
    private TrackingRepositiory trackingRepository;

    @Autowired
    public TrackingService(TrackingRepositiory trackingRepository) {
        this.trackingRepository = trackingRepository;
    }
   

    public List<Tracking> getTrackingsByShipmentId(Long shipmentId) {
        return trackingRepository.findAllTrackingsByShipmentId(shipmentId);
    }

    public Tracking addTracking(Tracking tracking) {

        return trackingRepository.save(tracking);
    }

    public List<TrackingEntity> getAllTrackings() {
        return trackingRepository.findAll();
    }

}
