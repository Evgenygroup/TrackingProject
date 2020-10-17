package com.evgeny.track.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingDTO {
    @Nullable
    Long trackingId;
    String status;
    Long shipmentId;
    Date eventDate;
}
