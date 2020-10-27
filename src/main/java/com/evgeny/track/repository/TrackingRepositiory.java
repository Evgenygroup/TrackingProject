package com.evgeny.track.repository;
import com.evgeny.track.entity.TrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepositiory extends JpaRepository<TrackingEntity,Long> {
    List<TrackingEntity> findAllTrackingsByShipmentId(Long ShipmentId);
}


