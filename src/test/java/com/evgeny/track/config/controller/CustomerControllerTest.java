package com.evgeny.track.config.controller;

import com.evgeny.track.config.TestConfig;
import com.evgeny.track.controller.CustomerController;
import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.entity.TrackingEntity;
import com.evgeny.track.exception.CustomerNotFoundException;
import com.evgeny.track.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestConfig.class)
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private CustomerService service;

    private CustomerEntity savedCustomer;

    @Before
    public void init() {
        savedCustomer = mockCustomerEntity();
    }


    @Test
    public void testGetAllCustomersSuccess() throws Exception {

        when(service.getCustomerList())
                .thenReturn(createListOfCustomers());

        mvc.perform(get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value(savedCustomer.getId()))
                .andExpect(jsonPath("$[0].name").value(savedCustomer.getName()))
                .andExpect(jsonPath("$[1].id").value("7"))
                .andExpect(jsonPath("$[1].name").value("John Smith"))
                .andExpect(jsonPath("$[2].id").value("8"))
                .andExpect(jsonPath("$[2].name").value("Sherlock Holmes"));

        verify(service, times(1)).getCustomerList();
    }

    @Test
    public void testGetCustomerByIdSuccess() throws Exception {
        when(service.getCustomerByCustomerId(savedCustomer.getId()))
                .thenReturn(savedCustomer);

        mvc.perform(get("/api/customers/{id}", savedCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Evgeny Grazhdansky"));

        verify(service, times(1)).getCustomerByCustomerId(savedCustomer.getId());

    }

    @Test
    public void testGetCustomerByIdNotFound() throws Exception {
        Long fakeId = 123456L;
        when(service.getCustomerByCustomerId(fakeId))
                .thenThrow(new CustomerNotFoundException(fakeId));

        mvc.perform(get("/api/customers/{id}", fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testCreateNewCustomerSuccess() throws Exception {
        CustomerEntity newCustomer = new CustomerEntity(null, "Evgeny Grazhdansky");

        when(service.createCustomer(newCustomer)).thenReturn(savedCustomer);

        mvc.perform(post("/api/customers")
                .content("{\"name\": \"Evgeny Grazhdansky\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("6"))
                .andExpect(jsonPath("$.name").value("Evgeny Grazhdansky"));

        verify(service, times(1)).createCustomer(newCustomer);
    }


    @Test
    public void removeCustomerByIdSuccess() throws Exception {
        Long id = 123L;
        mvc.perform(delete("/api/customers/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        verify(service, times(1)).deleteCustomer(id);
    }


    @Test
    public void removeCustomerByIdNotFound() throws Exception {
        Long fakeId = 123L;
        doThrow(new CustomerNotFoundException(fakeId)).when(service).deleteCustomer(fakeId);
        mvc.perform(delete("/api/customers/{id}", fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(service, times(1)).deleteCustomer(fakeId);
    }


    private List<CustomerEntity> createListOfCustomers() {
        CustomerEntity customer1 = savedCustomer;
        CustomerEntity customer2 = new CustomerEntity(7L, "John Smith");
        CustomerEntity customer3 = new CustomerEntity(8L, "Sherlock Holmes");
        return Arrays.asList(customer1, customer2, customer3);
    }


    private CustomerEntity mockCustomerEntity() {
        return new CustomerEntity(6L, "Evgeny Grazhdansky");
    }


}



