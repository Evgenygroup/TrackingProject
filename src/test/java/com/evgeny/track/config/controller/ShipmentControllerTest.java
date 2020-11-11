package com.evgeny.track.config.controller;

import com.evgeny.track.config.TestConfig;
import com.evgeny.track.controller.ShipmentController;
import com.evgeny.track.dto.ShipmentNameDTO;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.service.ShipmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@RunWith(SpringRunner.class)
@WebMvcTest(ShipmentController.class)
public class ShipmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShipmentService service;

    @Test
    public void testGetShipmentsByCustomerIdSuccess() throws Exception {
        Long customerId = 7L;
        when(service.getShipmentsByCustomerId(customerId))
                .thenReturn(createListOfShipments());

        mvc.perform(get("/api/shipments/{customerId}/shipments", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].description").value("Bosch"))
                .andExpect(jsonPath("$[1].id").value("3"))
                .andExpect(jsonPath("$[1].description").value("TP-Link"))
                .andExpect(jsonPath("$[2].id").value("4"))
                .andExpect(jsonPath("$[2].description").value("Notebook"));

        verify(service, times(1)).getShipmentsByCustomerId(customerId);
    }

    @Test
    public void testGetShipmentsByCustomerIdNotFound() throws Exception {
        Long fakeCustomerId = 7L;
        when(service.getShipmentsByCustomerId(fakeCustomerId))
                .thenThrow(new CustomerNotFoundException(fakeCustomerId));

        mvc.perform(get("/api/shipments/{customerId}/shipments", fakeCustomerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(service, times(1)).getShipmentsByCustomerId(fakeCustomerId);
    }


    @Test
    public void testAddShipmentByCustomerIdSuccess() throws Exception {
        ShipmentEntity shipmentEntity = new ShipmentEntity(null, "Sony TV", null, null);
        ShipmentEntity savedShipmentEntity = new ShipmentEntity(5L, "Sony TV", null, null);

        when(service.addShipmentByCustomerId(7L, shipmentEntity)).thenReturn(savedShipmentEntity);

        mvc.perform(post("/api/shipments/{customerId}/shipment", 7L)
                .content("{\"description\": \"Sony TV\",\"customerId\":\"7\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.description").value("Sony TV"));

        verify(service, times(1)).addShipmentByCustomerId(7L, shipmentEntity);
    }

    @Test
    public void testAddShipmentByCustomerIdNotFound() throws Exception {
        ShipmentEntity shipment = new ShipmentEntity(null, "Sony TV", null, null);
        Long fakecustomerId = 12345L;
        when(service.addShipmentByCustomerId(fakecustomerId, shipment))
                .thenThrow(new CustomerNotFoundException(fakecustomerId));

        mvc.perform(post("/api/shipments/{customerId}/shipment", fakecustomerId)
                .content("{\"description\": \"Sony TV\",\"customerId\":\"12345\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(service, times(1)).addShipmentByCustomerId(fakecustomerId, shipment);
    }


    @Test
    public void testGetCustomerNameByShipmentIdSuccess() throws Exception {
        Long shipmentId = 25L;
        when(service.getCustomerByShipmentId(shipmentId))
                .thenReturn(new ShipmentNameDTO("Evgeny Gr", "Notebook"));

        mvc.perform(get("/api/shipments/{shipmentId}", shipmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name").value("Evgeny Gr"))
                .andExpect(jsonPath("$.description").value("Notebook"));

        verify(service, times(1)).getCustomerByShipmentId(shipmentId);

    }

    @Test
    public void testGetCustomerNameByShipmentIdNotFound() throws Exception {
        Long shipmentId = 25L;
        when(service.getCustomerByShipmentId(shipmentId))
                .thenThrow(new CustomerNotFoundException(null));

        mvc.perform(get("/api/shipments/{shipmentId}", shipmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(service, times(1)).getCustomerByShipmentId(shipmentId);

    }

    private List<ShipmentEntity> createListOfShipments() {

        ShipmentEntity shipment1 = new ShipmentEntity(2L, "Bosch", null, null);
        ShipmentEntity shipment2 = new ShipmentEntity(3L, "TP-Link", null, null);
        ShipmentEntity shipment3 = new ShipmentEntity(4L, "Notebook", null, null);
        return Arrays.asList(shipment1, shipment2, shipment3);
    }

}
