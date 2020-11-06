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


    public ShipmentEntity addShipmentByCustomerId(Long customerId, ShipmentEntity shipment) {
        CustomerEntity customer = customerRepository
                .getById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        shipment.setCustomer(customer);

        return shipmentRepository.save(shipment);
    }

    public List<ShipmentEntity> getShipmentsByCustomerId(Long customerId) {
        customerRepository
                .getById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        return shipmentRepository.findAllShipmentsByCustomerId(customerId);
    }

    public ShipmentNameDTO getCustomerByShipmentId(long shipmentId){
        ShipmentEntity shipment = shipmentRepository
                .findById(shipmentId).orElseThrow(()->new ShipmentNotFoundException(shipmentId));
      //  Long customerId =shipment.getCustomer().getId();
       // Optional<CustomerEntity> customer = customerRepository.getById(customerId);
      //  customer.orElseThrow(()->new CustomerNotFoundException(customerId));
        return new ShipmentNameDTO(shipment.getCustomer().getName(),shipment.getDescription());
    }


}
