package com.zee.course.auth.payment.authpaymentservice.controller;


import com.zee.course.auth.payment.authpaymentservice.dto.CreatePaymentRequestDto;
import com.zee.course.auth.payment.authpaymentservice.dto.CreatePaymentResponseDto;
import com.zee.course.auth.payment.authpaymentservice.dto.StatusResponseDto;
import com.zee.course.auth.payment.authpaymentservice.permission.Permission;
import com.zee.course.auth.payment.authpaymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {
    private final PaymentService paymentService;

    @PreAuthorize(value = "hasAuthority('"+ Permission.INITIATE +"')")
    @PostMapping(path = "initiate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatePaymentResponseDto> initiatePayment(@Valid @RequestBody CreatePaymentRequestDto requestDto,
                                                                    JwtAuthenticationToken token){
        return new ResponseEntity<>(
                paymentService.initiatePayment(requestDto, token),
                HttpStatus.OK
        );
    }

    @PreAuthorize(value = "hasAuthority('"+ Permission.STATUS +"')")
    @GetMapping(path = "status")
    public ResponseEntity<StatusResponseDto> status(@RequestParam("transactionReference") String transactionReference,
                                                    JwtAuthenticationToken token){
        return new ResponseEntity<>(
                paymentService.getPaymentStatus(transactionReference, token),
                HttpStatus.OK
        );
    }

}
