package com.evgeny.track.service;

import com.evgeny.track.dto.CustomerDto;
import com.evgeny.track.dto.ShipmentNameDTO;
import com.evgeny.track.entity.Customer;
import com.evgeny.track.entity.Shipment;
import com.evgeny.track.entity.Tracking;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.repositiory.CustomerRepository;
import com.evgeny.track.repositiory.ShipmentRepository;
import com.evgeny.track.repositiory.TrackingRepositiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackingService {


    private CustomerRepository customerRepository;
    private ShipmentRepository shipmentRepository;
    private TrackingRepositiory trackingRepository;

    @Autowired
    public TrackingService(CustomerRepository customerRepository,
                           ShipmentRepository shipmentRepository,
                           TrackingRepositiory trackingRepository) {
        this.customerRepository = customerRepository;
        this.shipmentRepository = shipmentRepository;
        this.trackingRepository = trackingRepository;
    }

    public Customer addCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.getById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setName(customerDto.getName());
        return customerRepository.save(customer);
    }


    public Shipment addShipment(Long customerId, Shipment shipment) {
        Customer customer = customerRepository
                .getById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        shipment.setCustomer(customer);
        return shipmentRepository.save(shipment);
    }

    public List<Tracking> getTrackingsByShipmentId(int shipmentId ) {
        List<Tracking> trackings = trackingRepository.findAll();

        return trackings.stream().filter( tracking -> null != tracking.getShipmentId() && tracking.getShipmentId() == shipmentId ).collect(Collectors.toList());
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



    public Tracking addTracking(Tracking tracking) {


        return trackingRepository.save(tracking);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Tracking getCustomerByCustomerId(Long customerId) {
        return trackingRepository.getOne(customerId);
    }

    public List<Tracking> getAllTracking() {
        return trackingRepository.findAll();
    }

    public List<Shipment> getAllShipment() {
        return shipmentRepository.findAll();
    }
}
