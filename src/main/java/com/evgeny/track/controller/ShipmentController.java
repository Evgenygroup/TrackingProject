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
    public ShipmentController(ShipmentService service) {
        this.service = service;
    }


    @GetMapping("/api/shipments/{customerId}/shipments")
    public List<ShipmentDTO> getShipmentsByCustomerId(@PathVariable Long customerId){
        return service.getShipmentsByCustomerId(customerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @PostMapping("/api/shipments/{customerId}/shipment")
    public ShipmentDTO addShipmentByCustomerId(@RequestBody ShipmentDTO shipmentDto,
                                               @PathVariable Long customerId) {

        ShipmentEntity shipment = service.addShipmentByCustomerId(customerId, convertToEntity(shipmentDto));
        return convertToDTO(shipment);

    }


    @GetMapping("/api/shipments/{shipmentId}")
    public ShipmentNameDTO getCustomerNameByShipmentId(@PathVariable Long shipmentId){
        return service.getCustomerByShipmentId(shipmentId);
    }


    private ShipmentEntity convertToEntity(ShipmentDTO shipmentDTO) {
        ShipmentEntity shipment = new ShipmentEntity();
        shipment.setDescription(shipmentDTO.getDescription());
        shipment.setId(shipmentDTO.getId());
        return  shipment;
    }

    private ShipmentDTO convertToDTO(ShipmentEntity shipment) {
        ShipmentDTO shipmentDto = new ShipmentDTO();
        shipmentDto.setDescription((shipment.getDescription()));
        shipmentDto.setId(shipment.getId());
        return  shipmentDto;
    }

}
