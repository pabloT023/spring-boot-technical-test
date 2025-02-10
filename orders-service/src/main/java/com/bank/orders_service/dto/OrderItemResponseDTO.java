package com.bank.orders_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private Long productId;
    private String productTitle;
    private BigDecimal price;
    private Integer quantity;
}
