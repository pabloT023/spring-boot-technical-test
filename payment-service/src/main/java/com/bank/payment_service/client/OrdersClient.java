package com.bank.payment_service.client;

import com.bank.payment_service.dto.OrderDTO;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "orders-service", url = "http://localhost:8080/api/orders")
public interface OrdersClient {
    //Get product by id
    @GetMapping("/{orderId}")
    default Optional<OrderDTO> getOrderById(@PathVariable Long orderId) {
        try {
            return Optional.ofNullable(getOrder(orderId));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @GetMapping("/{orderId}")
    OrderDTO getOrder(@PathVariable Long orderId);

    @RequestMapping(method = RequestMethod.PATCH, value = "/orders/{orderId}/payment")
    OrderDTO setOrderAsPaid(@PathVariable Long orderId);
}
