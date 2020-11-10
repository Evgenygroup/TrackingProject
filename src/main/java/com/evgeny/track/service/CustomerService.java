package com.evgeny.track.service;

import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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

        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }


    public CustomerEntity updateCustomer(Long id,CustomerEntity customerEntity) {

        CustomerEntity customer = getCustomerByCustomerId(id);
        customer.setName(customerEntity.getName());

        return customerRepository.save(customer);
    }


    public void deleteCustomer (Long id) {

        getCustomerByCustomerId(id);
        customerRepository.deleteById(id);
    }

   }
