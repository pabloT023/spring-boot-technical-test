package com.bank.orders_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private BigDecimal totalPrice;
    private List<OrderItemResponseDTO> items;
    private Boolean isPaid;
    private Boolean isCanceled;

}
