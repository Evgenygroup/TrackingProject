package com.evgeny.track.controller;

import com.evgeny.track.dto.ShipmentDTO;
import com.evgeny.track.dto.TrackingDTO;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.entity.TrackingEntity;
import com.evgeny.track.service.TrackingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<TrackingDTO> getTrackingsByShipmentId(@PathVariable("shipmentId") int shipmentId ) {
        return  service.getTrackingsByShipmentId(shipmentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());


    }

    private TrackingDTO convertToDTO(TrackingEntity tracking) {
        TrackingDTO trackingDto = new TrackingDTO();
        trackingDto.setTrackingId(tracking.getId());
        trackingDto.setStatus(tracking.getStatus());
        trackingDto.setShipmentId(tracking.getShipmentId());
        trackingDto.setEventDate(tracking.getEventDate());

        return  trackingDto;
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
