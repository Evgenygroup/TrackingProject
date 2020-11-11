package com.evgeny.track.config.service;

import com.evgeny.track.dto.ShipmentNameDTO;
import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.exception.ShipmentNotFoundException;
import com.evgeny.track.repository.CustomerRepository;
import com.evgeny.track.repository.ShipmentRepository;
import com.evgeny.track.service.ShipmentService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
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
        ShipmentEntity shipmentToSave = new ShipmentEntity();
        shipmentToSave.setDescription("testShipment");
        ShipmentEntity shipmentSaved =new ShipmentEntity(3L,"testShipment",customer,null);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(shipmentRepository.save(shipmentToSave)).thenReturn(shipmentSaved);

        ShipmentEntity shipmentSavedActual = shipmentService.addShipmentByCustomerId(1L, shipmentToSave);

        assertEquals(shipmentSavedActual.getCustomer().getName(),customer.getName());
        assertEquals(shipmentSavedActual.getCustomer().getId(),customer.getId());
        assertEquals(shipmentSavedActual.getDescription(),shipmentToSave.getDescription());

        verify(shipmentRepository, times(1)).save(shipmentToSave);

    }

    @Test
    public void testAddShipmentByCustomerIdNotFound(){

        Long wrongId=2345L;
        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                shipmentService.addShipmentByCustomerId(wrongId,any()));

        verify(shipmentRepository, times(0)).save(any());
        assertEquals("Customer not found", exception.getMessage());
    }


    @Test
    public void testGetShipmentsByCustomerIdSuccess(){

        CustomerEntity customer = new CustomerEntity(1L, "Evgeny Grazhdansky");
        ShipmentEntity shipment1 = new ShipmentEntity(1L,"description1",customer,null);
        ShipmentEntity shipment2 = new ShipmentEntity(2L,"description2",customer,null);
        List<ShipmentEntity> shipmentsExpected = Arrays.asList(shipment1,shipment2);

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(shipmentRepository.findAllShipmentsByCustomerId(customer.getId()))
                .thenReturn(shipmentsExpected);
        List<ShipmentEntity> shipments = shipmentService.getShipmentsByCustomerId(customer.getId());
        assertArrayEquals(shipmentsExpected.toArray(), shipments.toArray());
        verify(customerRepository, times(1)).findById(customer.getId());
    }

    @Test
    public void testGetShipmentsByCustomerIdNotFound(){
        Long wrongId=2345L;
        Exception exception = assertThrows(CustomerNotFoundException.class, () ->
                shipmentService.getShipmentsByCustomerId(wrongId));

        verify(shipmentRepository, times(0)).findAllShipmentsByCustomerId(any());
        assertEquals("Customer not found", exception.getMessage());
    }


    @Test
    public void testGetCustomerByShipmentId() {
        CustomerEntity customer = new CustomerEntity(1L, "Evgeny Grazhdansky");
        ShipmentEntity shipment = new ShipmentEntity(
                1L,
                "test shipment",
                customer,
                null);

        when(shipmentRepository.findById(shipment.getId())).thenReturn(Optional.of(shipment));
        ShipmentNameDTO customerByShipmentIdExpected = new ShipmentNameDTO(
                customer.getName(),shipment.getDescription());
        ShipmentNameDTO customerByShipmentIdActual = shipmentService
                .getCustomerByShipmentId(shipment.getId());

        assertEquals(customerByShipmentIdExpected.getName(),customerByShipmentIdActual.getName());
        assertEquals(customerByShipmentIdExpected.getDescription(),customerByShipmentIdActual.getDescription());
    }



    @Test
    public void testGetCustomerByShipmentIdNotFound() {
        Long wrongId=2345L;
        Exception exception = assertThrows(ShipmentNotFoundException.class, () ->
                shipmentService.getCustomerByShipmentId(wrongId));

        verify(shipmentRepository, times(1)).findById(wrongId);
        assertEquals("Shipment not found", exception.getMessage());


    }

    }
