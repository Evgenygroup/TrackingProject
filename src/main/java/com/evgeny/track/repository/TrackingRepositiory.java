package com.evgeny.track.repository;
import com.evgeny.track.entity.Shipment;
import com.evgeny.track.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepositiory extends JpaRepository<Tracking,Long> {
    List<Tracking> findAllTrackingsByShipmentId(Long ShipmentId);


