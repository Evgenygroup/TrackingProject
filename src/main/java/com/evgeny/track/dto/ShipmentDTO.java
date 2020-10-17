package com.evgeny.track.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {

    @Nullable
    private Long id;
    private String description;
    private Long customerId;
    private List<TrackingDTO> statusesDTO;
}
