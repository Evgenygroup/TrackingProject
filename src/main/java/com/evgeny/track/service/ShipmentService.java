package com.evgeny.track.service;

import com.evgeny.track.dto.ShipmentNameDTO;
import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.exception.ShipmentNotFoundException;
import com.evgeny.track.repository.CustomerRepository;
import com.evgeny.track.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShipmentService {


    private CustomerRepository customerRepository;
    private ShipmentRepository shipmentRepository;


    @Autowired
    public ShipmentService(CustomerRepository customerRepository,
                           ShipmentRepository shipmentRepository) {
        this.customerRepository = customerRepository;
        this.shipmentRepository = shipmentRepository;

    }


    public ShipmentEntity addShipmentByCustomerId(Long customerId, ShipmentEntity shipment) {

        CustomerEntity customer = getCustomerById(customerId);
        shipment.setCustomer(customer);

        return shipmentRepository.save(shipment);
    }


    public List<ShipmentEntity> getShipmentsByCustomerId(Long customerId) {
        getCustomerById(customerId);

        return shipmentRepository.findAllShipmentsByCustomerId(customerId);
    }


    public ShipmentNameDTO getCustomerByShipmentId(long shipmentId) {
        ShipmentEntity shipment = shipmentRepository
                .findById(shipmentId).orElseThrow(() -> new ShipmentNotFoundException(shipmentId));
        return new ShipmentNameDTO(shipment.getCustomer().getName(), shipment.getDescription());
    }


    private CustomerEntity getCustomerById(Long customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }


}
