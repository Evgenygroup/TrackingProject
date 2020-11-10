package com.evgeny.track.config.service;

import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.repository.CustomerRepository;
import com.evgeny.track.service.CustomerService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {

        CustomerEntity newCustomer = new CustomerEntity(null, "Evgeny Grazhdansky");
        CustomerEntity savedCustomer = new CustomerEntity(1L, "Evgeny Grazhdansky");
        when(customerRepository.save(newCustomer)).thenReturn(savedCustomer);

        CustomerEntity returnedCustomer = customerService.createCustomer(newCustomer);

        assertEquals(returnedCustomer.getName(), newCustomer.getName());
        verify(customerRepository, times(1)).save(newCustomer);
        ;


    }

    @Test
    public void testGetAllCustomersNoCustomers() {

        List<CustomerEntity> listOfSavedCustomers = Arrays.asList();

        when(customerRepository.findAll()).thenReturn(listOfSavedCustomers);

        List<CustomerEntity> customerListResult = customerService.getCustomerList();

        assertArrayEquals(customerListResult.toArray(), listOfSavedCustomers.toArray());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCustomers() {
        CustomerEntity customer1 = new CustomerEntity(6L, "Evgeny Grazhdansky");
        CustomerEntity customer2 = new CustomerEntity(7L, "John Smith");
        CustomerEntity customer3 = new CustomerEntity(8L, "Sherlock Holmes");
        List<CustomerEntity> listOfSavedCustomers = Arrays.asList(customer1, customer2, customer3);

        when(customerRepository.findAll()).thenReturn(listOfSavedCustomers);

        List<CustomerEntity> customerListResult = customerService.getCustomerList();

        assertArrayEquals(customerListResult.toArray(), listOfSavedCustomers.toArray());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerByCustomerIdFound() {
        CustomerEntity savedCustomer = new CustomerEntity(1L, "Evgeny Grazhdansky");

        when(customerRepository.findById(savedCustomer.getId())).thenReturn(Optional.of(savedCustomer));
        CustomerEntity customerFound = customerService.getCustomerByCustomerId(savedCustomer.getId());

        assertEquals(savedCustomer.getId(), customerFound.getId());
        assertEquals(savedCustomer.getName(), customerFound.getName());

    }

    @Test
    public void testGetCustomerByCustomerIdNotFound() {
        Long wrongId = 2345L;
        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                customerService.getCustomerByCustomerId(wrongId));

        verify(customerRepository, times(1)).findById(any());
        assertEquals("Customer not found", exception.getMessage());

    }
    @Test
    public void testUpdateCustomer(){
        CustomerEntity newCustomer = new CustomerEntity(1L,"Evgeny Grazhdansky");
        CustomerEntity oldCustomer = new CustomerEntity(1L,"John Smith");

        when(customerRepository.findById(newCustomer.getId())).thenReturn(Optional.of(oldCustomer));
        when(customerRepository.save(newCustomer)).thenReturn(newCustomer);
        CustomerEntity customerActual = customerService.updateCustomer(1L, newCustomer);

        assertEquals(newCustomer.getId(),customerActual.getId());
        assertEquals(newCustomer.getName(),customerActual.getName());

        verify(customerRepository, times(1)).save(any());
    }


    @Test
    public void testUpdateCustomerNotFound(){

        Long wrongId=2345L;
        CustomerEntity newCustomer = new CustomerEntity(1L,"Evgeny Grazhdansky");

        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                customerService.updateCustomer(wrongId,newCustomer));

        verify(customerRepository, times(1)).findById(wrongId);
        verify(customerRepository, times(0)).save(any());
        assertEquals("Customer not found", exception.getMessage());

    }




    @Test
    public void testDeleteCustomerSuccess() {
        CustomerEntity customerToDelete = new CustomerEntity(1L, "Evgeny Grazhdansky");
        when(customerRepository.findById(customerToDelete.getId()))
                .thenReturn(Optional.of(customerToDelete));

        customerService.deleteCustomer(customerToDelete.getId());
        verify(customerRepository, times(1))
                .deleteById(customerToDelete.getId());
    }

    @Test
    public void testDeletecustomerNotFound() {

        Long wrongId = 2345L;
        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                customerService.deleteCustomer(wrongId));

        verify(customerRepository, times(0)).deleteById(wrongId);
        assertEquals("Customer not found", exception.getMessage());


    }

}




