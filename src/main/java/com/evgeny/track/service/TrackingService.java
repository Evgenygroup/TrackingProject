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


    public List<TrackingEntity> getTrackingsByShipmentId(int shipmentId) {
        List<TrackingEntity> trackings = trackingRepository.findAll();
        return trackings.stream()
                .filter(tracking -> null != tracking.getShipmentId() && tracking.getShipmentId() == shipmentId)
                .collect(Collectors.toList());
    }


    public TrackingEntity addTracking(TrackingEntity tracking) {
        return trackingRepository.save(tracking);
    }

    public List<TrackingEntity> getAllTrackings() {
        return trackingRepository.findAll();
    }

}
