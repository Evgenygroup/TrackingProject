package com.evgeny.track.controller;

import com.evgeny.track.dto.ShipmentDTO;
import com.evgeny.track.dto.ShipmentNameDTO;
import com.evgeny.track.entity.Shipment;
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


    @GetMapping("/api/customers/{id}/shipments")
    public List<ShipmentDTO> getShipmentsByCustomerId(@PathVariable long id){
        return service.getShipmentsByCustomerId(id)
                .stream()
                .map(this::convertShipmentToDTOShipment)
                .collect(Collectors.toList());
    }


    @PostMapping("/api/customers/{customerId}/shipments")
    public Shipment addShipmentByCustomerId(@RequestBody ShipmentDTO shipmentDto, @PathVariable Long customerId) {
        return service.addShipmentByCustomerId(customerId, convertShipmentDtoToShipment(shipmentDto));
    }


    @GetMapping("/api/shipments/{shipmentId}")
    public ShipmentNameDTO getCustomerNameByShipmentId(@PathVariable long shipmentId){
        return service.getCustomerByShipmentId(shipmentId);
    }


    private Shipment convertShipmentDtoToShipment(ShipmentDTO shipmentDTO) {
        Shipment shipment = new Shipment();
        shipment.setDescription(shipmentDTO.getDescription());
        shipment.setId(shipmentDTO.getId());
        return  shipment;
    }

    private ShipmentDTO convertShipmentToDTOShipment(Shipment shipment) {
        ShipmentDTO shipmentDto = new ShipmentDTO();
        shipmentDto.setDescription((shipment.getDescription()));
        shipmentDto.setId(shipment.getId());
        return  shipmentDto;
    }

}
