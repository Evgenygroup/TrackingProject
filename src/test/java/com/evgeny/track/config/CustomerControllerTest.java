package com.evgeny.track.config;

import com.evgeny.track.controller.CustomerController;
import com.evgeny.track.dto.CustomerDto;
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
import java.util.Date;
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

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService service;

    private CustomerEntity savedCustomer;

    @Before
    public void init(){
        savedCustomer=mockCustomerEntity();
    }

    @Test
    public void testCreateCustomerSuccess() throws Exception {
        CustomerEntity newCustomer = new CustomerEntity(null,"Evgeny Grazhdansky");

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

        mvc.perform(get("/api/customers/{id}",savedCustomer.getId())
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

        mvc.perform(get("/api/customers/{id}",fakeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testEditCustomerSuccess() throws Exception {
        CustomerEntity newCustomer = new CustomerEntity(null,"Evgeny Grazhdansky");
        String json = mapper.writeValueAsString(new CustomerDto(6L,"Evgeny Grazhdansky"));


        when(service.updateCustomer(6L,newCustomer)).thenReturn(savedCustomer);

        mvc.perform(put("/api/customers/6")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("6"))
                .andExpect(jsonPath("$.name").value("Evgeny Grazhdansky"));

        verify(service, times(1)).createCustomer(newCustomer);
    }


/*@Test
    public void updateEmployeeById_success() throws Exception {
        EmployeeDto updatedEmployeeDto = mockEmployeeUpdateDto();
        Employee updatedEmployee = mockUpdatedEmployee();
        String id = updatedEmployee.getId();
        String json = mapper.writeValueAsString(updatedEmployeeDto);

        when(this.employeeService.saveUpdatedEmployee(id, updatedEmployeeDto)).thenReturn(updatedEmployee);

        mvc.perform(put("/employees/{id}", id).with(httpBasic("user", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedEmployee.getId()))
                .andExpect(jsonPath("$.fullName").value(updatedEmployee.getFullName()))
                .andExpect(jsonPath("$.email").value(updatedEmployee.getEMail()))
                .andExpect(jsonPath("$.hobbies").value(updatedEmployee.getHobbies()))
                .andDo(print());
    }
*/

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
        CustomerEntity customer2=new CustomerEntity(7L,"John Smith");
        CustomerEntity customer3=new CustomerEntity(8L,"Sherlock Holmes");
        return Arrays.asList(customer1, customer2,customer3);
    }


    private CustomerEntity mockCustomerEntity(){
        return new CustomerEntity(6L,"Evgeny Grazhdansky");
    }

    private CustomerDto mockReturnedCustomerDto(){
        return new CustomerDto(6L,"Evgeny Grazhdansky");
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
