package com.evgeny.track.service;

import com.evgeny.track.dto.CustomerDto;
import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private CustomerRepository customerRepository;


    @Autowired
    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }



    public CustomerEntity createCustomer(CustomerEntity customer) {

        return customerRepository.save(customer);
    }



    public List<CustomerEntity> getCustomerList() {

        return customerRepository.findAll();
    }



    public CustomerEntity getCustomerByCustomerId(Long customerId) {

        return customerRepository.getOne(customerId);
    }


    public CustomerEntity updateCustomer(Long id,CustomerEntity customerEntity) {

        CustomerEntity customer = customerRepository.getById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setName(customerEntity.getName());

        return customerRepository.save(customer);
    }


    public void deleteCustomer (Long id) {

        customerRepository.findById(id).orElseThrow(() ->new CustomerNotFoundException(id));
        customerRepository.deleteById(id);
    }

   }
