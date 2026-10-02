package com.zee.course.auth.payment.authpaymentservice.service;



import com.zee.course.auth.payment.authpaymentservice.datatype.PaymentStatus;
import com.zee.course.auth.payment.authpaymentservice.dto.CreatePaymentRequestDto;
import com.zee.course.auth.payment.authpaymentservice.dto.CreatePaymentResponseDto;
import com.zee.course.auth.payment.authpaymentservice.dto.StatusResponseDto;
import com.zee.course.auth.payment.authpaymentservice.dto.UserTransactionResponse;
import com.zee.course.auth.payment.authpaymentservice.entity.Payment;
import com.zee.course.auth.payment.authpaymentservice.exception.PaymentServiceException;
import com.zee.course.auth.payment.authpaymentservice.repository.PaymentRepository;
import com.zee.course.auth.payment.authpaymentservice.util.PaymentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;


/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Sep, 2026
 */

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentUtil paymentUtil;


    @Transactional
    public CreatePaymentResponseDto initiatePayment(CreatePaymentRequestDto createPaymentRequestDto,
                                                    JwtAuthenticationToken token) {
        String initiator = paymentUtil.getUsernameFromAccessToken(token);
        boolean isExists = paymentRepository
                .existsByTransactionReferenceAndInitiator(createPaymentRequestDto.transactionReference(), initiator);

        if(isExists){
            throw new PaymentServiceException(
                    "transaction exists with reference: " + createPaymentRequestDto.transactionReference(),
                    HttpStatus.BAD_REQUEST
            );
        }

        Payment payment = new Payment();
        payment.setTransactionReference(createPaymentRequestDto.transactionReference());
        payment.setPrice(createPaymentRequestDto.price());
        payment.setMode(createPaymentRequestDto.mode());
        payment.setStatus(PaymentStatus.IN_PROGRESS);
        payment.setCreatedAt(Instant.now());

        payment.setInitiator(initiator);
        paymentRepository.save(payment);
        return new CreatePaymentResponseDto(createPaymentRequestDto.transactionReference(), createPaymentRequestDto.mode());
    }


    @Transactional(readOnly = true)
    public StatusResponseDto getPaymentStatus(String transactionReference, JwtAuthenticationToken token){

        //extract initiator and query only transactions related to initiator
        Optional<Payment> payment = paymentRepository.findByTransactionReferenceAndInitiator(
                transactionReference, paymentUtil.getUsernameFromAccessToken(token)
        );

        if(payment.isEmpty())
            throw new PaymentServiceException(
                    "Transaction does not exist for transactionReference: " + transactionReference,
                    HttpStatus.BAD_REQUEST);

        return new StatusResponseDto(transactionReference, payment.get().getStatus());
    }

    @Transactional(readOnly = true)
    public UserTransactionResponse getUserTransactions(LocalDate startDate){

        final Instant convertedDate = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return new UserTransactionResponse(
                paymentRepository.findByCreatedAtGreaterThan(convertedDate)
                        .stream()
                        .map(payment -> new UserTransactionResponse
                                        .Transaction(
                                        payment.getInitiator(),
                                        payment.getCreatedAt()
                                )
                        )
                        .toList()
        );
    }
}
