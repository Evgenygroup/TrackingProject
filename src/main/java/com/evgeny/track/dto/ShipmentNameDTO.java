package com.evgeny.track.dto;

import lombok.Data;

@Data
public class ShipmentNameDTO {
    String description;
    String name;

    public ShipmentNameDTO(String name,String description){
        this.name =name;
        this.description=description;
    }
}
