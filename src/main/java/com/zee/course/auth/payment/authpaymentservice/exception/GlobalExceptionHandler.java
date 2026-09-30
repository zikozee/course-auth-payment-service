package com.zee.course.auth.payment.authpaymentservice.exception;


import com.zee.course.auth.payment.authpaymentservice.datatype.MessageType;
import com.zee.course.auth.payment.authpaymentservice.datatype.PaymentFieldError;
import com.zee.course.auth.payment.authpaymentservice.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    ResponseEntity<ApiResponse<?>> onMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        List<PaymentFieldError> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .distinct()
                .map(e -> new PaymentFieldError(e.getField(), e.getDefaultMessage(), e.getRejectedValue()))
                .toList();

        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .messageType(MessageType.ERROR)
                .successful(false)
                .fieldErrors(fieldErrors)
                .data("Invalid user input")
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(PaymentServiceException.class)
    public final ResponseEntity<ApiResponse<String>> onAuthServiceException(PaymentServiceException ex) {

        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .messageType(MessageType.ERROR)
                .successful(false)
                .errorMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(apiResponse, ex.getStatus());
    }

}
