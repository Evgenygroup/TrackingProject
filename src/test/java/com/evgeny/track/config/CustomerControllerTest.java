package com.evgeny.track.config;

import com.evgeny.track.controller.CustomerController;
import com.evgeny.track.dto.CustomerDto;
import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.entity.TrackingEntity;
import com.evgeny.track.service.CustomerService;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private CustomerService service;

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerEntity newCustomer = new CustomerEntity(
                null,
                "Ivan Petrov"
        );
        CustomerEntity savedCustomerEntity = new CustomerEntity(
                1L,
                "Ivan Petrov");
        when(service.createCustomer(newCustomer)).thenReturn(savedCustomerEntity);

        mvc.perform(post("/api/customers")
                .content("{\"name\": \"Ivan Petrov\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Ivan Petrov"));

        verify(service, times(1)).createCustomer(newCustomer);
    }

    @Test
    public void testGetAllCustomers() throws Exception {

        when(service.getCustomerList())
                .thenReturn(createListOfCustomers());

        mvc.perform(get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].id").value("6"))
                .andExpect(jsonPath("$[0].name").value("Ivan Petrov"))
                .andExpect(jsonPath("$[1].id").value("7"))
                .andExpect(jsonPath("$[1].name").value("John Smith"))
                .andExpect(jsonPath("$[2].id").value("8"))
                .andExpect(jsonPath("$[2].name").value("Sherlock Holmes"));
    }

    private List<CustomerEntity> createListOfCustomers() {

        CustomerEntity customer1 = new CustomerEntity(6L,"Ivan Petrov");
        CustomerEntity customer2=new CustomerEntity(7L,"John Smith");
        CustomerEntity customer3=new CustomerEntity(8L,"Sherlock Holmes");

        return Arrays.asList(customer1, customer2,customer3);
    }

}

/*Date date =new Date();
        TrackingEntity tracking1 =new TrackingEntity (1L,"delivered",3L,date);
        TrackingEntity tracking2 =new TrackingEntity(2L,"returned",3L,date);
        TrackingEntity tracking3 =new TrackingEntity(3L,"initiated",4L,date);
        TrackingEntity tracking4 =new TrackingEntity(4L,"cancelled",4L,date);
        TrackingEntity tracking5 =new TrackingEntity(5L,"returned",5L, date);


       List<TrackingEntity> statuses1 = Arrays.asList(tracking1,tracking2);
       List<TrackingEntity> statuses2=Arrays.asList(tracking3,tracking4);
       List<TrackingEntity> statuses3=Arrays.asList(tracking5);

        ShipmentEntity shipment1 = new ShipmentEntity(
                3L,
                "Bosch",
                customer1,
                statuses1);
        ShipmentEntity shipment2 = new ShipmentEntity(
                2L,
                "TP-Link",
                customer2,
                statuses2
        );
        ShipmentEntity shipment3=new ShipmentEntity(
                4L,
                "Notebook",
                customer2,
                statuses3
        );

         // .andExpect(jsonPath("$[0].shipments").exists())
*/
