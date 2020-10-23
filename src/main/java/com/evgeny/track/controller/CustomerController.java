package com.evgeny.track.controller;
import com.evgeny.track.entity.Customer;
import com.evgeny.track.dto.CustomerDto;
import com.evgeny.track.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class CustomerController {

    private final CustomerService service;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService service, ModelMapper modelMapper) {
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


    @PostMapping("/api/customers")
    public Customer addCustomer(@RequestBody CustomerDto customer) {
        return service.addCustomer(customer);
    }

    @PutMapping("/api/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customer) {
        return service.updateCustomer(id, customer);
    }



}
