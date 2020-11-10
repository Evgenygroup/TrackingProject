package com.evgeny.track.config.service;

import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.entity.ShipmentEntity;
import com.evgeny.track.entity.TrackingEntity;
import com.evgeny.track.exception.ShipmentNotFoundException;
import com.evgeny.track.repository.ShipmentRepository;
import com.evgeny.track.repository.TrackingRepositiory;
import com.evgeny.track.service.TrackingService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class TrackingSeviceTest {

    @Mock
    private TrackingRepositiory trackingRepositiory;

    @Mock
    private ShipmentRepository shipmentRepository;

    @InjectMocks
    private TrackingService trackingService;

    @Test
    public void testGetTrackingsByShipmentIdSuccess() {
        Long shipmentId = 1L;
        Date date = new Date();

        TrackingEntity tracking1 = new TrackingEntity(1L, "initiated", shipmentId, date);
        TrackingEntity tracking2 = new TrackingEntity(2L, "delivered", shipmentId, date);
        TrackingEntity tracking3 = new TrackingEntity(3L, "returned", shipmentId, date);
        List trackings = Arrays.asList(tracking1, tracking2, tracking3);

        ShipmentEntity shipment = new ShipmentEntity(1L,
                "description1",
                new CustomerEntity(2L, "testCustomer"),
                trackings);

        when(shipmentRepository.findById(shipmentId)).thenReturn(Optional.of(shipment));
        when(trackingRepositiory.findAllTrackingsByShipmentId(shipmentId)).thenReturn(trackings);

        List<TrackingEntity> trackingsActual = trackingService.getTrackingsByShipmentId(shipmentId);
        assertArrayEquals(trackings.toArray(), trackingsActual.toArray());
        verify(trackingRepositiory, times(1)).findAllTrackingsByShipmentId(shipmentId);
        verify(shipmentRepository, times(1)).findById(shipmentId);
    }

    @Test
    public void testGetTrackingsByShipmentIdNotFound() {
        Long wrongId = 2345L;
        Exception exception = assertThrows(ShipmentNotFoundException.class, () ->
                trackingService.getTrackingsByShipmentId(wrongId));

        verify(trackingRepositiory, times(0)).findAllTrackingsByShipmentId(any());
        verify(shipmentRepository, times(1)).findById(wrongId);
        assertEquals("Shipment not found", exception.getMessage());
    }

    @Test
    public void testAddTrackingSuccess() {
        Long shipmentId = 1L;
        Date date = new Date();
        TrackingEntity tracking = new TrackingEntity(null, "initiated", shipmentId, date);
        TrackingEntity trackingSaved = new TrackingEntity(5L, "initiated", shipmentId, date);

        when(trackingRepositiory.save(tracking)).thenReturn(trackingSaved);

        TrackingEntity trackingActual = trackingRepositiory.save(tracking);
        assertEquals(trackingSaved, trackingActual);

    }

    @Test
    public void testAddTrackingShipmentIdNotFound() throws Exception {
        Long wrongShipmentId = 2345L;
        Date date = new Date();
        TrackingEntity tracking = new TrackingEntity(null, "initiated", wrongShipmentId, date);
        Exception exception = assertThrows(ShipmentNotFoundException.class, () ->
                trackingService.addTracking(tracking));

        verify(trackingRepositiory, times(0)).save(any());
        verify(shipmentRepository, times(1)).findById(wrongShipmentId);
        assertEquals("Shipment not found", exception.getMessage());
    }

}
