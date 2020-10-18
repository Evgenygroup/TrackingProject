package com.evgeny.track.config;


import com.evgeny.track.controller.CustomerController;
import com.evgeny.track.service.TrackingService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@Import(TestConfig.class)
public class TrackingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TrackingService service;



  /*  @Test
    public void testAddTracking() throws Exception {
        Tracking trackingEntity = new Tracking(null, "delivered",2L);
        Tracking savedTrackingEntity = new Tracking(1L, "delivered",2L);
        when(service.addTracking(trackingEntity)).thenReturn(savedTrackingEntity);

        mvc.perform(post("/api/shipments/2/trackings")
                .content("{\"status\": \"delivered\",\"shipmentId\":\"2\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.trackingId").value("1"))
                .andExpect(jsonPath("$.status").value("delivered"))
                .andExpect(jsonPath("$.shipmentId").value("2"));

        verify(service, times(1)).addTracking(trackingEntity);
    }*/
}

