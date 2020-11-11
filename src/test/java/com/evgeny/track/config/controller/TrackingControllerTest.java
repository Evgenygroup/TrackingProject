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

import java.util.Arrays;
import java.util.Date;
import java.util.List;


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
        Date date = new Date();
        Long shipmentId = 7L;
        TrackingEntity tracking1 = new TrackingEntity(1L, "initiated", shipmentId, date);
        TrackingEntity tracking2 = new TrackingEntity(2L, "delivered", shipmentId, date);
        TrackingEntity tracking3 = new TrackingEntity(3L, "returned", shipmentId, date);
        List trackings = Arrays.asList(tracking1, tracking2, tracking3);

        when(service.getTrackingsByShipmentId(shipmentId))
                .thenReturn(trackings);


        mvc.perform(get("/api/trackings/{shipmentId}/trackings", shipmentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].trackingId").value("1"))
                .andExpect(jsonPath("$[0].status").value("initiated"))
                .andExpect(jsonPath("$[0].shipmentId").value(shipmentId))
                .andExpect(jsonPath("$[0].eventDate").exists())
                .andExpect(jsonPath("$[1].trackingId").value("2"))
                .andExpect(jsonPath("$[1].status").value("delivered"))
                .andExpect(jsonPath("$[1].shipmentId").value(shipmentId))
                .andExpect(jsonPath("$[1].eventDate").exists())
                .andExpect(jsonPath("$[2].trackingId").value("3"))
                .andExpect(jsonPath("$[2].status").value("returned"))
                .andExpect(jsonPath("$[2].shipmentId").value(shipmentId))
                .andExpect(jsonPath("$[2].eventDate").exists());
        verify(service, times(1)).getTrackingsByShipmentId(shipmentId);
    }

}

