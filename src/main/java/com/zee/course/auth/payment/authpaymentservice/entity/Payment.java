package com.zee.course.auth.payment.authpaymentservice.entity;


import com.zee.course.auth.payment.authpaymentservice.datatype.Mode;
import com.zee.course.auth.payment.authpaymentservice.datatype.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Sep, 2026
 */

@Getter
@Setter
@Entity
@Table(name = "payment",
        indexes = {
                @Index(name = "idx_tran_details", columnList = "transaction_reference, initiator")
        }
)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionReference;
    private BigDecimal price;
    private String initiator;// user from auth server

    @Enumerated(EnumType.STRING)
    private Mode mode;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Instant createdAt;
}
