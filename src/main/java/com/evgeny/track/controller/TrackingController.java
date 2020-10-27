package com.evgeny.track.controller;

import com.evgeny.track.dto.TrackingDTO;
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


    @Autowired
    public TrackingController(TrackingService service) {
        this.service = service;
    }


    @GetMapping("/api/trackings/{shipmentId}/trackings")
    public List<TrackingDTO> getTrackingsByShipmentId(@PathVariable("shipmentId") long shipmentId) {
        return service.getTrackingsByShipmentId(shipmentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
/*getTrackingById( id: number ) {
    return this.http.get(`${this.baseUrl}/tracking/${id}`);
  }*/

    @PostMapping("/api/tracking")
    public TrackingDTO addTracking(@RequestBody TrackingDTO trackingDTO) {
        TrackingEntity tracking = convertToEntity(trackingDTO);
        return convertToDTO(service.addTracking(tracking));
    }


    private TrackingDTO convertToDTO(TrackingEntity tracking) {
        TrackingDTO trackingDto = new TrackingDTO();
        trackingDto.setTrackingId(tracking.getId());
        trackingDto.setStatus(tracking.getStatus());
        trackingDto.setShipmentId(tracking.getShipmentId());
        trackingDto.setEventDate(tracking.getEventDate());

        return trackingDto;

    }

    private TrackingEntity convertToEntity(TrackingDTO trackingDTO) {
        TrackingEntity tracking = new TrackingEntity();
        tracking.setStatus(trackingDTO.getStatus());
        tracking.setShipmentId(trackingDTO.getShipmentId());
        tracking.setEventDate(new Date());

        return tracking;
    }

}
