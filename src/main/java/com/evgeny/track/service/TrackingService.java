package com.evgeny.track.service;

import com.evgeny.track.entity.Tracking;
import com.evgeny.track.repository.ShipmentRepository;
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


    public List<Tracking> getTrackingsByShipmentId(int shipmentId) {
        List<Tracking> trackings = trackingRepository.findAll();
        return trackings.stream()
                .filter(tracking -> null != tracking.getShipmentId() && tracking.getShipmentId() == shipmentId)
                .collect(Collectors.toList());
    }


    public Tracking addTracking(Tracking tracking) {
        return trackingRepository.save(tracking);
    }

}
