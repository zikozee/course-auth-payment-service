package com.zee.course.auth.payment.authpaymentservice.controller;


import com.zee.course.auth.payment.authpaymentservice.datatype.MessageType;
import com.zee.course.auth.payment.authpaymentservice.dto.ApiResponse;
import com.zee.course.auth.payment.authpaymentservice.dto.UserTransactionResponse;
import com.zee.course.auth.payment.authpaymentservice.permission.Permission;
import com.zee.course.auth.payment.authpaymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @dev : Ezekiel Eromosei
 * @date : 02 Oct, 2026
 */



@RestController
@RequiredArgsConstructor
@RequestMapping(path = "system", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemController {
    private final PaymentService paymentService;

    @PreAuthorize(value = "hasAuthority('"+ Permission.SYSTEM_ACCESS +"')")
    @GetMapping
    public ResponseEntity<ApiResponse<UserTransactionResponse>> status(@RequestParam("startDate") LocalDate startDate){
        return new ResponseEntity<>(
                ApiResponse.<UserTransactionResponse>builder()
                        .successful(true)
                        .messageType(MessageType.SUCCESS)
                        .data(paymentService.getUserTransactions(startDate))
                        .build(),
                HttpStatus.OK
        );
    }

}
