package com.evgeny.track.service;

import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.entity.TrackingEntity;
import com.evgeny.track.exception.ShipmentNotFoundException;
import com.evgeny.track.repository.ShipmentRepository;
import com.evgeny.track.repository.TrackingRepositiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrackingService {
    private TrackingRepositiory trackingRepository;
    private ShipmentRepository shipmentRepository;

    @Autowired
    public TrackingService(TrackingRepositiory trackingRepository, ShipmentRepository shipmentRepository) {
        this.trackingRepository = trackingRepository;
        this.shipmentRepository = shipmentRepository;
    }


    public List<TrackingEntity> getTrackingsByShipmentId(Long shipmentId) {

        getShipmentById(shipmentId);

        return trackingRepository.findAllTrackingsByShipmentId(shipmentId);
    }


    public TrackingEntity addTracking(TrackingEntity tracking) {

        getShipmentById(tracking.getShipmentId());

        return trackingRepository.save(tracking);
    }


    private ShipmentEntity getShipmentById(Long shipmentId) {
        return shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException(shipmentId));
    }

}
