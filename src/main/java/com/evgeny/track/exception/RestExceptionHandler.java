package com.evgeny.track.exception;


import com.evgeny.track.dto.CustomerErrorDto;
import com.evgeny.track.dto.ShipmentErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(CustomerNotFoundException e, WebRequest request) {
        return handleExceptionInternal(
                e,
                new CustomerErrorDto(e.getCustomerId(), e.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(ShipmentNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(ShipmentNotFoundException e, WebRequest request) {
        return handleExceptionInternal(
                e,
                new ShipmentErrorDto(e.getShipmentId(), e.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }
}


