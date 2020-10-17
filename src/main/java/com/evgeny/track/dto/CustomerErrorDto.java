package com.evgeny.track.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerErrorDto {

    private Long customerId;
    private String message;
    private LocalDateTime date = LocalDateTime.now();

    public CustomerErrorDto(Long customerId, String message) {
        this.customerId = customerId;
        this.message = message;
    }
}
