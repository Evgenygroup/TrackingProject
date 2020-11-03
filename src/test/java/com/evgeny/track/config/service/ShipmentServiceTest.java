package com.evgeny.track.config.service;

import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.repository.CustomerRepository;
import com.evgeny.track.repository.ShipmentRepository;
import com.evgeny.track.service.CustomerService;
import com.evgeny.track.service.ShipmentService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private ShipmentService shipmentService;


    public void testAddShipmentByCustomerId() {
        Long customerId = 1L;
        ShipmentEntity shipmentToSave = new ShipmentEntity();
        shipmentToSave.setDescription("testShipment");
        shipmentToSave.setId(customerId);

        CustomerEntity newCustomer = new CustomerEntity(null, "Evgeny Grazhdansky");
        CustomerEntity savedCustomer = new CustomerEntity(1L, "Evgeny Grazhdansky");
        when(customerRepository.save(newCustomer)).thenReturn(savedCustomer);

      //  CustomerEntity returnCustomer = customerService.createCustomer(newCustomer);

     //   assertEquals(returnCustomer.getName(), newCustomer.getName());
        verify(customerRepository, times(1)).save(newCustomer);
        ;


    }

}
