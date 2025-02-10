package com.bank.payment_service.repository;

import com.bank.payment_service.model.Payment;
import com.bank.payment_service.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
