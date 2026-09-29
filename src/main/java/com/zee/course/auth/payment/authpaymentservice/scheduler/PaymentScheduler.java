package com.zee.course.auth.payment.authpaymentservice.scheduler;


import com.zee.course.auth.payment.authpaymentservice.datatype.PaymentStatus;
import com.zee.course.auth.payment.authpaymentservice.entity.Payment;
import com.zee.course.auth.payment.authpaymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

@Component
@RequiredArgsConstructor
public class PaymentScheduler {
    private final PaymentRepository paymentRepository;

    public static SecureRandom random = new SecureRandom();

    @Transactional
    @Scheduled(fixedDelay = 8000L, initialDelay = 1500L)
    public void processTransaction(){

        // this is not ideal just for demo purpose
        List<Payment> paymentList = paymentRepository.findByStatus(PaymentStatus.IN_PROGRESS);

        List<Payment> updatePayments = paymentList.stream()
                .map(payment -> {
                    payment.setStatus(
                            random.nextBoolean()
                                    ? PaymentStatus.SUCCESSFUL
                                    : PaymentStatus.FAILED
                    );
                    return payment;
                })
                .toList();

        paymentRepository.saveAll(updatePayments);
    }
}
