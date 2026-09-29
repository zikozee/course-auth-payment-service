package com.zee.course.auth.payment.authpaymentservice.dto;


import com.zee.course.auth.payment.authpaymentservice.datatype.Mode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

public record CreatePaymentRequestDto(
        @NotBlank(message = "transactionReference is mandatory")
        String transactionReference,
        @Min(value = 10, message = "price must be greater than 10")
        BigDecimal price,
        @NotNull(message = "mode is required")
        Mode mode
) {
}
