package com.zee.course.auth.payment.authpaymentservice.datatype;


/**
 * @dev : Ezekiel Eromosei
 * @date : 30 Sep, 2026
 */

public record PaymentFieldError(
        String field,
        String message,
        Object rejectedInput
) { }