package com.zee.course.auth.payment.authpaymentservice.dto;


import com.zee.course.auth.payment.authpaymentservice.datatype.Mode;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

public record CreatePaymentResponseDto(String transactionReference, Mode mode) {
}
