package com.evgeny.track.service;

import com.evgeny.track.dto.ShipmentNameDTO;
import com.evgeny.track.entity.Customer;
import com.evgeny.track.entity.Shipment;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.repository.CustomerRepository;
import com.evgeny.track.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ShipmentService {


    private CustomerRepository customerRepository;
    private ShipmentRepository shipmentRepository;


    @Autowired
    public ShipmentService(CustomerRepository customerRepository,
                           ShipmentRepository shipmentRepository){
        this.customerRepository = customerRepository;
        this.shipmentRepository = shipmentRepository;

    }


    public Shipment addShipmentByCustomerId(Long customerId, Shipment shipment) {
        Customer customer = customerRepository
                .getById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        shipment.setCustomer(customer);
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getShipmentsByCustomerId(Long customerId) {
        customerRepository
                .getById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        return shipmentRepository.findAllShipmentsByCustomerId(customerId);
    }

    public ShipmentNameDTO getCustomerByShipmentId(long shipmentId){
        Shipment shipment = shipmentRepository.getOne(shipmentId);
        long customerId =shipment.getCustomer().getId();
        Optional<Customer> customer = customerRepository.getById(customerId);
        customer.orElseThrow(()->new CustomerNotFoundException(customerId));
        return new ShipmentNameDTO(customer.get().getName(),shipment.getDescription());
    }


}
