package com.evgeny.track.controller;

import com.evgeny.track.dto.TrackingDTO;
import com.evgeny.track.entity.TrackingEntity;
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

    @GetMapping("/api/trackings/{shipmentId}/trackings")
    public List<TrackingEntity> getTrackingsByShipmentId(@PathVariable("shipmentId") int shipmentId ) {
        List<TrackingEntity> trackingList = service.getTrackingsByShipmentId(shipmentId);

        return trackingList;
    }

    @PostMapping("/api/trackings/{shipmentId}/trackings")
    TrackingDTO addTrackingByShipmentId(@RequestBody TrackingDTO tracking, @PathVariable long shipmentId) {

        Date date = new Date();
        TrackingEntity trackingEntity = new TrackingEntity(tracking.getTrackingId(),tracking.getStatus(), shipmentId, date);

        return modelMapper.map(service.addTracking(trackingEntity), TrackingDTO.class);
    }

    @PostMapping("/api/tracking")
    public TrackingEntity addTracking(@RequestBody TrackingDTO tracking) {

        Date date = new Date();
        TrackingEntity dbTracking = new TrackingEntity(null, tracking.getStatus(), tracking.getShipmentId(), date);

        return service.addTracking(dbTracking);
    }

    @GetMapping("/api/tracking")
    public List<TrackingEntity> getAllTrackings() {
        return service.getAllTrackings();}

}
