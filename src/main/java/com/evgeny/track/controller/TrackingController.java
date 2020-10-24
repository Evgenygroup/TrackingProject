package com.evgeny.track.controller;

import com.evgeny.track.dto.TrackingDTO;
import com.evgeny.track.entity.Tracking;
import com.evgeny.track.service.TrackingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class TrackingController {
    private TrackingService service;
    private ModelMapper modelMapper;

    @Autowired
    public TrackingController(TrackingService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

//    @GetMapping("/api/shipment/{id}")
    @GetMapping("/api/trackings/{shipmentId}/trackings")
    public List<Tracking> getTrackingsByShipmentId(@PathVariable("shipmentId") int shipmentId ) {
        List<Tracking> trackingList = service.getTrackingsByShipmentId(shipmentId);

        return trackingList;
    }

  //  @PostMapping("/api/shipments/{id}/trackings")
    @PostMapping("/api/trackings/{shipmentId}/trackings")
    TrackingDTO addTrackingByShipmentId(@RequestBody TrackingDTO tracking, @PathVariable long shipmentId) {

        Date date = new Date();
        Tracking trackingEntity = new Tracking(tracking.getTrackingId(),tracking.getStatus(), shipmentId, date);

        return modelMapper.map(service.addTracking(trackingEntity), TrackingDTO.class);
    }

    @PostMapping("/api/tracking")
    public Tracking addTracking(@RequestBody TrackingDTO tracking) {

        Date date = new Date();
        Tracking dbTracking = new Tracking(null, tracking.getStatus(), tracking.getShipmentId(), date);

        return service.addTracking(dbTracking);
    }

    @GetMapping("/api/tracking")
    public List<Tracking> getAllTrackingWithShipmentsAndTrackings() {
        return service.getAllTracking();}

}
