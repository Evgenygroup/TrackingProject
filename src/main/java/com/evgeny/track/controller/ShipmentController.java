package com.evgeny.track.controller;

import com.evgeny.track.dto.ShipmentDTO;
import com.evgeny.track.dto.ShipmentNameDTO;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.service.ShipmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShipmentController {

    private ShipmentService service;


    @Autowired
    public ShipmentController(ShipmentService service, ModelMapper modelMapper) {
        this.service = service;
    }


    @GetMapping("/api/shipments/{customerId}/shipments")
    public List<ShipmentDTO> getShipmentsByCustomerId(@PathVariable long customerId){
        return service.getShipmentsByCustomerId(customerId)
                .stream()
                .map(this::convertShipmentToDTOShipment)
                .collect(Collectors.toList());
    }


    @PostMapping("/api/shipments/{customerId}/shipment")
    public ShipmentEntity addShipmentByCustomerId(@RequestBody ShipmentDTO shipmentDto, @PathVariable Long customerId) {
        return service.addShipmentByCustomerId(customerId, convertShipmentDtoToShipment(shipmentDto));
    }


    @GetMapping("/api/shipments/{shipmentId}")
    public ShipmentNameDTO getCustomerNameByShipmentId(@PathVariable long shipmentId){
        return service.getCustomerByShipmentId(shipmentId);
    }


    private ShipmentEntity convertShipmentDtoToShipment(ShipmentDTO shipmentDTO) {
        ShipmentEntity shipment = new ShipmentEntity();
        shipment.setDescription(shipmentDTO.getDescription());
        shipment.setId(shipmentDTO.getId());
        return  shipment;
    }

    private ShipmentDTO convertShipmentToDTOShipment(ShipmentEntity shipment) {
        ShipmentDTO shipmentDto = new ShipmentDTO();
        shipmentDto.setDescription((shipment.getDescription()));
        shipmentDto.setId(shipment.getId());
        return  shipmentDto;
    }

}
