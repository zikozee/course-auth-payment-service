package com.zee.course.auth.payment.authpaymentservice.repository;


import com.zee.course.auth.payment.authpaymentservice.datatype.PaymentStatus;
import com.zee.course.auth.payment.authpaymentservice.entity.Payment;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Sep, 2026
 */

@Repository
public interface PaymentRepository extends ListCrudRepository<Payment, Long> {

    Optional<Payment> findByTransactionReferenceAndInitiator(String transactionReference, String initiator);
    boolean existsByTransactionReferenceAndInitiator(String transactionReference, String initiator);

    List<Payment> findByStatus(PaymentStatus paymentStatus);
}
