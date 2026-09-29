package com.zee.course.auth.payment.authpaymentservice.dto;

import com.zee.course.auth.payment.authpaymentservice.datatype.PaymentStatus;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

public record StatusResponseDto(String transactionReference, PaymentStatus status) {
}
