package com.evgeny.track.controller;

import com.evgeny.track.dto.ShipmentNameDTO;
import com.evgeny.track.dto.TrackingDTO;
import com.evgeny.track.entity.Shipment;
import com.evgeny.track.entity.Tracking;
import com.evgeny.track.service.TrackingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ShipmentController {

    private TrackingService service;

    private ModelMapper modelMapper;

    @Autowired
    public ShipmentController(TrackingService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/shipment")
    public List<Shipment> getAllTrackingWithShipmentsAndTrackings() {
        return service.getAllShipment();
    }

    @GetMapping("/api/shipment/{id}")
    public List<Tracking> getTrackingsById(@PathVariable("id") int shipmentId ) {
        List<Tracking> trackingList = service.getTrackingsByShipmentId(shipmentId);

        return trackingList;
    }
    @GetMapping("/api/shipments/{shipmentId}")
    public ShipmentNameDTO getCustomerNameByShipmentId(@PathVariable long shipmentId){
        return service.getCustomerByShipmentId(shipmentId);
    }

    @PostMapping("/api/shipments/{id}/trackings")
    TrackingDTO addTracking(@RequestBody TrackingDTO tracking, @PathVariable long id) {

        Date date = new Date();
        Tracking trackingEntity = new Tracking(tracking.getTrackingId(),tracking.getStatus(), id, date);

        return modelMapper.map(service.addTracking(trackingEntity), TrackingDTO.class);
    }

}
