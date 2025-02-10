package com.bank.payment_service.repository;

import com.bank.payment_service.model.PaymentMethod;
import com.bank.payment_service.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}
