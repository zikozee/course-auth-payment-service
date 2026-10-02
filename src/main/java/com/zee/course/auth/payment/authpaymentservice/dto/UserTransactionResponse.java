package com.zee.course.auth.payment.authpaymentservice.dto;


import java.time.Instant;
import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 02 Oct, 2026
 */

public record UserTransactionResponse(List<Transaction> transactions) {

    public record Transaction(String user, Instant transactionCreatedDate){}
}
