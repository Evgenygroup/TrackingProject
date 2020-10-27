package com.evgeny.track.controller;
import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.dto.CustomerDto;
import com.evgeny.track.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/api/customers")
    public List<CustomerDto> getAllCustomers() {
       List<CustomerEntity> customers = service.getCustomerList();
       return customers.stream()
               .map(this::convertToDto)
               .collect(Collectors.toList());
    }


    @GetMapping("/api/customers/{id}")
    public CustomerDto getCustomerById(@PathVariable long id){
       return convertToDto(service.getCustomerByCustomerId(id));
    }


    @PostMapping("/api/customers")
    public CustomerDto createNewCustomer(@RequestBody CustomerDto customerDto) {
        CustomerEntity customer = convertToEntity(customerDto);
        CustomerEntity customerCreated =service.createCustomer(customer);
        return convertToDto(customerCreated);
    }


    @PutMapping("/api/customers/{id}")
    public CustomerDto editCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {

        CustomerEntity customer = service.updateCustomer(id, convertToEntity(customerDto));
        return convertToDto(customer);
    }


    private CustomerDto convertToDto(CustomerEntity customerEntity) {
        CustomerDto customerDto =new CustomerDto();
        customerDto.setId(customerEntity.getId());
        customerDto.setName(customerEntity.getName());
        return customerDto;
    }

    private CustomerEntity convertToEntity(CustomerDto customerDto) {
        CustomerEntity customer=new CustomerEntity();
        customer.setName(customerDto.getName());
        return customer;
    }



}
