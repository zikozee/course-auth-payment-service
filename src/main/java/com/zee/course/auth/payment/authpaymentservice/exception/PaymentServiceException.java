package com.zee.course.auth.payment.authpaymentservice.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

@Getter
public class PaymentServiceException extends RuntimeException {

    private final HttpStatus status;

    public PaymentServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public PaymentServiceException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
