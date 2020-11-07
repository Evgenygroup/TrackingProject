package com.evgeny.track.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipmentErrorDto {

    private Long shipmentId;
    private String message;
    private LocalDateTime date = LocalDateTime.now();

    public ShipmentErrorDto(Long shipmentId, String message) {
        this.shipmentId = shipmentId;
        this.message = message;
    }
}
