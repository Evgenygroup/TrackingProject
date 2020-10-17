package com.evgeny.track.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment,Long> {

    List<Shipment> findAllShipmentsByCustomerId(Long CustomerId);
}
