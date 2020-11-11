package com.evgeny.track.repository;

import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity,Long> {

    List<ShipmentEntity> findAllShipmentsByCustomerId(Long CustomerId);

    Optional<ShipmentEntity> getById(Long id);
}
