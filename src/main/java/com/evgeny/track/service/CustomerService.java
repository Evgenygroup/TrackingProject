package com.evgeny.track.service;

import com.evgeny.track.dto.CustomerDto;
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
import java.util.stream.Collectors;

@Service
public class CustomerService {


    private CustomerRepository customerRepository;


    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer addCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        return customerRepository.save(customer);
    }


    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }


    public Customer getCustomerByCustomerId(Long customerId) {
        return customerRepository.getOne(customerId);
    }


    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.getById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setName(customerDto.getName());
        return customerRepository.save(customer);
    }


}
