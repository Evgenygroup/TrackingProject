package com.evgeny.track.config.controller;


import com.evgeny.track.config.TestConfig;
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
        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY).parse("2020-11-20");
   //   Date date =null;


        TrackingEntity tracking1 =new TrackingEntity (1L,"initiated",3L,date);
        TrackingEntity tracking2 =new TrackingEntity(2L,"delivered",4L,date);
        TrackingEntity tracking3 =new TrackingEntity(3L,"returned",5L,date);
        List list =Arrays.asList(tracking1,tracking2,tracking3);
        Long shipmentId = 7L;
        String date1 ="2020-11-19";
   //   String date1 =null;
        when(service.getTrackingsByShipmentId(shipmentId))
                .thenReturn(list);


        mvc.perform(get("/api/trackings/{shipmentId}/trackings",shipmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].trackingId").value("1"))
                .andExpect(jsonPath("$[0].status").value("initiated"))
                .andExpect(jsonPath("$[0].shipmentId").value("3"))
                .andExpect(jsonPath("$[0].eventDate").value(date1))
                .andExpect(jsonPath("$[1].trackingId").value("2"))
                .andExpect(jsonPath("$[1].status").value("delivered"))
                .andExpect(jsonPath("$[1].shipmentId").value("4"))
                .andExpect(jsonPath("$[1].eventDate").value(date1))
                .andExpect(jsonPath("$[2].trackingId").value("3"))
                .andExpect(jsonPath("$[2].status").value("returned"))
                .andExpect(jsonPath("$[2].shipmentId").value("5"))
                .andExpect(jsonPath("$[2].eventDate").value(date1));
        verify(service, times(1)).getTrackingsByShipmentId(shipmentId);
    }

    private List<TrackingEntity> createListOfTrackings() throws Exception {

      Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY).parse("2020-11-20");



        TrackingEntity tracking1 =new TrackingEntity (1L,"initiated",3L,date);
        TrackingEntity tracking2 =new TrackingEntity(2L,"delivered",4L,date);
        TrackingEntity tracking3 =new TrackingEntity(3L,"returned",5L,date);

        return Arrays.asList(tracking1,tracking2,tracking3);
    }
    @Test
    public void testAddTracking() throws Exception {
        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY).parse("2020-11-20");
        TrackingEntity trackingEntity = new TrackingEntity(null, "delivered", 2L,null);
        TrackingEntity savedTrackingEntity = new TrackingEntity(1L, "delivered", 2L,date);
           when(service.addTracking(trackingEntity)).thenReturn(savedTrackingEntity);

        mvc.perform(post("/api/tracking")
                .content("{\"status\":\"delivered\",\"shipmentId\":\"2\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.status").value("delivered"))
                .andExpect(jsonPath("$.shipmentId").value("2"));

           verify(service, times(1)).addTracking(trackingEntity);
    }
}

