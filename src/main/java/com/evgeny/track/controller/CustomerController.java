package com.evgeny.track.controller;


import com.evgeny.track.dto.CustomerDto;
import com.evgeny.track.dto.ShipmentDTO;
import com.evgeny.track.entity.Customer;
import com.evgeny.track.entity.Shipment;
import com.evgeny.track.service.TrackingService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {

    private final TrackingService service;
    private final ModelMapper modelMapper;

    public CustomerController(TrackingService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/customers")
    public List<Customer> getAllCustomersWithShipmentsAndTrackings() {
        return service.getAllCustomers();
    }

    @GetMapping("/api/customers/{id}")
    public CustomerDto getCustomerById(@PathVariable long id){
       return modelMapper.map(service.getCustomerByCustomerId(id), CustomerDto.class);
    }


    @GetMapping("/api/customers/{id}/shipments")
    public List<ShipmentDTO> getShipmentsByCustomerId(@PathVariable long id){
        return service.getShipmentsByCustomerId(id)
                .stream()
                .map(this::convertShipmentToDTOShipment)
                .collect(Collectors.toList());
    }


        private ShipmentDTO convertShipmentToDTOShipment(Shipment shipment) {
        ShipmentDTO shipmentDto = new ShipmentDTO();
        shipmentDto.setDescription((shipment.getDescription()));
        shipmentDto.setId(shipment.getId());
        return  shipmentDto;
    }


    @PostMapping("/api/customers")
    public Customer addCustomer(@RequestBody CustomerDto customer) {
        return service.addCustomer(customer);
    }

    @PutMapping("/api/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customer) {
        return service.updateCustomer(id, customer);
    }

    @PostMapping("/api/customers/{customerId}/shipments")
    public Shipment addShipment(@RequestBody ShipmentDTO shipmentDto, @PathVariable Long customerId) {
        return service.addShipment(customerId, convertShipmentDtoToShipment(shipmentDto));
    }

    private Shipment convertShipmentDtoToShipment(ShipmentDTO shipmentDTO) {
        Shipment shipment = new Shipment();
        shipment.setDescription(shipmentDTO.getDescription());
        shipment.setId(shipmentDTO.getId());
        return  shipment;
    }

}
