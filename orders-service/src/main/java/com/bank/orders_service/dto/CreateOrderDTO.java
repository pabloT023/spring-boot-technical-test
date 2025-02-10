package com.bank.orders_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CreateOrderDTO {

    @NotNull(message = "customerName is required")
    private String customerName;

    @NotNull(message = "orderItems are required")
    @Valid
    private List<CreateOrderItemDTO> items;

}
