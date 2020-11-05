package com.evgeny.track.config.service;

import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.repository.CustomerRepository;
import com.evgeny.track.repository.ShipmentRepository;
import com.evgeny.track.service.CustomerService;
import com.evgeny.track.service.ShipmentService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class ShipmentServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ShipmentRepository shipmentRepository;


    @InjectMocks
    private ShipmentService shipmentService;

    @Test
    public void testAddShipmentByCustomerId() {
        CustomerEntity customer = new CustomerEntity(1L, "Evgeny Grazhdansky");
        Long customerId = 1L;
        ShipmentEntity shipmentToSave = new ShipmentEntity();
        shipmentToSave.setDescription("testShipment");
        shipmentToSave.setId(customerId);
        CustomerRepository spyRepo = Mockito.spy(CustomerRepository.class);
     //   Mockito.doReturn(Optional.of(customer)).when(spyRepo).getById(Mockito.any(Long.class));
        when(customerRepository.getById(customer.getId())).thenReturn(Optional.of(customer));
        ShipmentEntity shipmentSaved = shipmentService.addShipmentByCustomerId(1L, shipmentToSave);

     //   assertEquals(shipmentSaved.getCustomer().getName(),customer.getName());
        assertEquals(shipmentSaved.getCustomer().getId(),customerId);
        assertNotNull(shipmentToSave.getDescription());

        verify(shipmentRepository, times(1)).save(shipmentToSave);

    }
    @Test
    public void testGetShipmentsByCustomerId(){

        CustomerEntity customer = new CustomerEntity(1L, "Evgeny Grazhdansky");
        ShipmentEntity shipment1 = new ShipmentEntity(1L,"description1",customer,null);
        ShipmentEntity shipment2 = new ShipmentEntity(2L,"description2",customer,null);
        List<ShipmentEntity> shipmentsExpected = Arrays.asList(shipment1,shipment2);

        when(customerRepository.getById(customer.getId())).thenReturn(Optional.of(customer));
        when(shipmentRepository.findAllShipmentsByCustomerId(customer.getId()))
                .thenReturn(shipmentsExpected);
        List<ShipmentEntity> shipments = shipmentService.getShipmentsByCustomerId(customer.getId());
        assertArrayEquals(shipmentsExpected.toArray(), shipments.toArray());
        verify(customerRepository, times(1)).getById(customer.getId());
    }

    @Test
    public void testGetShipmentsByCustomerIdNotFound(){
        Long wrongId=2345L;
        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                shipmentService.getShipmentsByCustomerId(wrongId));

        verify(shipmentRepository, times(0)).findAllShipmentsByCustomerId(any());
        assertEquals("Customer not found", exception.getMessage());
    }

}
