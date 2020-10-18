package com.evgeny.track.config;

import com.evgeny.track.controller.CustomerController;
import com.evgeny.track.dto.CustomerDto;
import com.evgeny.track.entity.Customer;
import com.evgeny.track.service.TrackingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TrackingService service;

    @Test
    public void testAddCustomer() throws Exception {
        CustomerDto newCustomer = new CustomerDto(
                null,
                "Ivan Petrov"
        );
        Customer savedCustomerEntity = new Customer(
                1L,
                "Ivan Petrov");
        when(service.addCustomer(newCustomer)).thenReturn(savedCustomerEntity);

        mvc.perform(post("/api/customers")
                .content("{\"name\": \"Ivan Petrov\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Ivan Petrov"));

        verify(service, times(1)).addCustomer(newCustomer);
    }

    /*@Test
    public void testGetAllCustomersWithShipmentsAndTrackings() throws Exception {
        when(service.getAllCustomers())
                .thenReturn(createListOfCustomers());

        mvc.perform(get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("Ivan"))
                .andExpect(jsonPath("$[0].shipments").exists())
                .andExpect(jsonPath("$[0].customerId").value("7"))
                .andExpect(jsonPath("$[1].name").value("Petr"))
                .andExpect(jsonPath("$[1].shipments").exists())
                .andExpect(jsonPath("$[1].customerId").value("6"));

    }

    private List<Customer> createListOfCustomers() {

        Tracking tracking1 =new Tracking(1L,"delivered",3L);
        Tracking tracking2 =new Tracking(2L,"returned",3L);
        Tracking tracking3 =new Tracking(3L,"initiated",4L);
        Tracking tracking4 =new Tracking(4L,"cancelled",4L);
        Tracking tracking5 =new Tracking(5L,"returned",5L);


       List<Tracking> statuses1 =Arrays.asList(tracking1,tracking2);
       List<Tracking> statuses2=Arrays.asList(tracking3,tracking4);
       List<Tracking> statuses3=Arrays.asList(tracking5);


        Customer customer1 = new Customer(
                7L,
                "Ivan");
        Customer customer2=new Customer(6L,
                "Petr");

        Shipment shipment1 = new Shipment(
                3L,
                "Bosch",
                customer1,
                statuses1);
        Shipment shipment2 = new Shipment(
                2L,
                "TP-Link",
                customer2,
                statuses2
        );
        Shipment shipment3=new Shipment(
                4L,
                "Notebook",
                customer2,
                statuses3
        );


        return Arrays.asList(customer1, customer2);
    }



    @Test
    public void testAddShipment() throws Exception {
        Shipment shipmentEntity = new Shipment(
                null,
                "Sony TV",
                7L,
                null);
        Shipment savedShipmentEntity = new Shipment(
                5L,
                "Sony TV",
                7L,
                null);
        when(service.addShipment(shipmentEntity)).thenReturn(savedShipmentEntity);

        mvc.perform(post("/api/customers/7/shipments")
                .content("{\"description\": \"Sony TV\",\"customerId\":\"7\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.shipmentId").value("5"))
                .andExpect(jsonPath("$.description").value("Sony TV"))
                .andExpect(jsonPath("$.customerId").value("7"));

        verify(service, times(1)).addShipment(shipmentEntity);
    }*/

}
