package com.evgeny.track.controller;


import com.evgeny.track.dto.TrackingDTO;
import com.evgeny.track.entity.Tracking;
import com.evgeny.track.service.TrackingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class TrackingController {

    private TrackingService service;

    @Autowired
    public TrackingController(TrackingService service, ModelMapper modelMapper) {
        this.service = service;
    }

    @GetMapping("/api/tracking")
    public List<Tracking> getAllTrackingWithShipmentsAndTrackings() {
        return service.getAllTracking();
    }

    @PostMapping("/api/tracking")
    public Tracking addTracking(@RequestBody TrackingDTO tracking) {

        Date date = new Date();
        Tracking dbTracking = new Tracking(null, tracking.getStatus(), tracking.getShipmentId(), date);

        return service.addTracking(dbTracking);
    }

}
