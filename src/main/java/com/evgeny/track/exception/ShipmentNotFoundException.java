package com.evgeny.track.exception;

import lombok.Getter;

public class ShipmentNotFoundException extends RuntimeException{

        @Getter
        private final Long shipmentId;

        public ShipmentNotFoundException(Long shipmentId) {
            super("Shipment not found");
            this.shipmentId = shipmentId;
        }
    }


