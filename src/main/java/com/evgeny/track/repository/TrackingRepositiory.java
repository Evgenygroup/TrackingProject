package com.evgeny.track.repository;

import com.evgeny.track.entity.TrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRepositiory extends JpaRepository<TrackingEntity,Long> {
}
