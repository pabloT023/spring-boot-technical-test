package com.bank.orders_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderItemDTO {

    @NotNull(message = "productId is required")
    private Long productId;

    @NotNull(message = "Order item price is required")
    private BigDecimal price;

    @NotNull(message = "Order quantity is required")
    @Min(value = 1, message = "The quantity must be greater than 0")
    private Integer quantity;
}
