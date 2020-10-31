package com.evgeny.track.config;


import com.evgeny.track.controller.TrackingController;
import com.evgeny.track.entity.TrackingEntity;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackingController.class)
@Import(TestConfig.class)
public class TrackingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TrackingService service;



    @Test
    public void testGetTrackingsByShipmentId() throws Exception {
        Long shipmentId = 7L;
        String date ="2020-11-19";
        when(service.getTrackingsByShipmentId(shipmentId))
                .thenReturn(createListOfTrackings());


        mvc.perform(get("/api/trackings/{shipmentId}/trackings",shipmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].trackingId").value("1"))
                .andExpect(jsonPath("$[0].status").value("initiated"))
                .andExpect(jsonPath("$[0].shipmentId").value("3"))
                .andExpect(jsonPath("$[0].eventDate").value(date))
                .andExpect(jsonPath("$[1].trackingId").value("2"))
                .andExpect(jsonPath("$[1].status").value("delivered"))
                .andExpect(jsonPath("$[1].shipmentId").value("4"))
                .andExpect(jsonPath("$[1].eventDate").value(date))
                .andExpect(jsonPath("$[2].trackingId").value("3"))
                .andExpect(jsonPath("$[2].status").value("returned"))
                .andExpect(jsonPath("$[2].shipmentId").value("5"))
                .andExpect(jsonPath("$[2].eventDate").value(date));
        verify(service, times(1)).getTrackingsByShipmentId(shipmentId);
    }

    private List<TrackingEntity> createListOfTrackings()throws Exception {

        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY).parse("2020-11-20");


        TrackingEntity tracking1 =new TrackingEntity (1L,"initiated",3L,date);
        TrackingEntity tracking2 =new TrackingEntity(2L,"delivered",4L,date);
        TrackingEntity tracking3 =new TrackingEntity(3L,"returned",5L,date);

        return Arrays.asList(tracking1,tracking2,tracking3);
    }




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

    @Test
    public void testAddTracking() throws Exception {
        TrackingEntity trackingEntity = new TrackingEntity(null, "delivered", 2L,null);
        TrackingEntity savedTrackingEntity = new TrackingEntity(1L, "delivered", 2L,null);
        //   when(service.addTracking(trackingEntity)).thenReturn(savedTrackingEntity);

        mvc.perform(post("/api/shipments/2/trackings")
                .content("{\"status\": \"delivered\",\"shipmentId\":\"2\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.trackingId").value("1"))
                .andExpect(jsonPath("$.status").value("delivered"))
                .andExpect(jsonPath("$.shipmentId").value("2"));

        //    verify(service, times(1)).addTracking(trackingEntity);
    }


}

