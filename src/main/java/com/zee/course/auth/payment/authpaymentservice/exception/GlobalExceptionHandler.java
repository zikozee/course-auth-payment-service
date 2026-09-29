package com.zee.course.auth.payment.authpaymentservice.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Response> onMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        List<PaymentFieldError> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .distinct()
                .map(e -> new PaymentFieldError(e.getField(), e.getDefaultMessage(), e.getRejectedValue()))
                .toList();

        Response response = new Response("Invalid user input", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(PaymentServiceException.class)
    public final ResponseEntity<Response> onAuthServiceException(PaymentServiceException ex) {
        Response response = new Response(ex.getMessage(), null);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Response(
            String message,
            List<PaymentFieldError> fieldErrors
    ) {}

    public record PaymentFieldError(
            String field,
            String message,
            Object rejectedInput
    ) { }
}
