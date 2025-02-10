package com.bank.payment_service.service;

import com.bank.payment_service.client.OrdersClient;
import com.bank.payment_service.dto.OrderDTO;
import com.bank.payment_service.dto.PaymentMethodResponseDTO;
import com.bank.payment_service.dto.PaymentResponseDTO;
import com.bank.payment_service.dto.PaymentTransactionResponseDTO;
import com.bank.payment_service.exception.*;
import com.bank.payment_service.model.Payment;
import com.bank.payment_service.model.PaymentMethod;
import com.bank.payment_service.model.PaymentTransaction;
import com.bank.payment_service.repository.PaymentMethodRepository;
import com.bank.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private OrdersClient ordersClient;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMethodRepository paymentMethodRepository){
        this.paymentRepository = paymentRepository;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    // Create a payment
    @Override
    public PaymentResponseDTO createPayment(Payment payment){

        OrderDTO orderDTO = ordersClient.getOrderById(payment.getOrderId()).
                orElseThrow(() -> new OrderNotFoundException(
                        "The order with ID " + payment.getOrderId() + " not found"
                        )
                );

        // Validate if the order isn't canceled or paid
        ValidateOrderCanceledAndPaidStatus(orderDTO);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for(PaymentTransaction paymentTransaction: payment.getPaymentTransactions()){
            totalAmount = totalAmount.add(paymentTransaction.getAmount());
        }

        if(orderDTO.getTotalPrice().equals(totalAmount)){
            //Set order as paid
            ordersClient.setOrderAsPaid(payment.getOrderId());
            payment.setIsCompleted(true);
        } else{
            throw new PaymentTotalAmountException(
                    "The amount entered does not match the expected total"
            );
        }

        paymentRepository.save(payment);

        return convertToPaymentResponseDTO(payment);
    }

    // Convert from payment model to payment response DTO
    private PaymentResponseDTO convertToPaymentResponseDTO(Payment payment){

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setId(payment.getId());
        paymentResponseDTO.setIsCompleted(payment.getIsCompleted());

        List<PaymentTransactionResponseDTO> paymentTransactionResponseDTOs = payment.getPaymentTransactions().stream()
                .map(this::convertToPaymentTransactionResponseDTO)
                .collect(Collectors.toList());

        paymentResponseDTO.setTransactions(paymentTransactionResponseDTOs);

        return paymentResponseDTO;
    }

    // Convert to payment transaction model to payment transaction response DTO
    private PaymentTransactionResponseDTO convertToPaymentTransactionResponseDTO(PaymentTransaction paymentTransaction){

        PaymentTransactionResponseDTO paymentTransactionResponseDTO = new PaymentTransactionResponseDTO();

        paymentTransactionResponseDTO.setId(paymentTransaction.getId());
        paymentTransactionResponseDTO.setAmount(paymentTransaction.getAmount());
        paymentTransactionResponseDTO.setPaymentMethod(
                convertToPaymentMethodResponseDTO(paymentTransaction.getPaymentMethod())
        );

        return paymentTransactionResponseDTO;
    }

    // Convert to payment method model to payment transaction response DTO
    private PaymentMethodResponseDTO convertToPaymentMethodResponseDTO(PaymentMethod paymentMethod){

        //Validate if the payment method exists
        PaymentMethod updatedPaymentMethod = paymentMethodRepository.findById(paymentMethod.getId())
                .orElseThrow(
                        ()-> new PaymentMethodNotFoundException(
                                "The payment method with ID " + paymentMethod.getId() + " not found"
                        )
                );

        PaymentMethodResponseDTO paymentMethodResponseDTO = new PaymentMethodResponseDTO();

        paymentMethodResponseDTO.setId(updatedPaymentMethod.getId());
        paymentMethodResponseDTO.setName(updatedPaymentMethod.getName());
        paymentMethodResponseDTO.setDescription(updatedPaymentMethod.getDescription());

        return paymentMethodResponseDTO;

    }

    // Validate if the order isn't canceled or paid
    private void ValidateOrderCanceledAndPaidStatus(OrderDTO orderDTO){
        if(orderDTO.getIsCanceled()){
            throw new OrderCanceledException(
                    "The order with ID " + orderDTO.getId() + " has been canceled and cannot be modified."
            );
        }

        if(orderDTO.getIsPaid()){
            throw new OrderPaidException(
                    "The order with ID " + orderDTO.getId() + " has been paid"
            );
        }
    }
}
