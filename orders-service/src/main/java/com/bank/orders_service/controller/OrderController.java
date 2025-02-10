package com.bank.orders_service.controller;

import com.bank.orders_service.dto.CreateOrderDTO;
import com.bank.orders_service.dto.CreateOrderItemDTO;
import com.bank.orders_service.dto.OrderResponseDTO;
import com.bank.orders_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // Endpoint to create an order
    @PostMapping
    public OrderResponseDTO createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO){
        return orderService.createOrder(createOrderDTO);
    }

    // Endpoint to get all orders
    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Endpoint to get an order by id
    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    // Endpoint to add an item to the order
    @PatchMapping("/{orderId}/items")
    public OrderResponseDTO addOrderItem(@Valid @RequestBody CreateOrderItemDTO createOrderItemDTO, @PathVariable Long orderId) {
        return orderService.addOrderItem(createOrderItemDTO,orderId);
    }

    // Endpoint to add an item
    @DeleteMapping("/{orderId}/items/product/{productId}")
    public OrderResponseDTO addOrderItem(@PathVariable Long orderId, @PathVariable Long productId) {
        return orderService.removeOrderItemByProduct(orderId,productId);
    }

    // Endpoint to cancel an order
    @PatchMapping("/{orderId}/cancel")
    public OrderResponseDTO cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    // Endpoint to cancel an order
    @PatchMapping("/{orderId}/payment")
    public OrderResponseDTO setOrderAsPaid(@PathVariable Long orderId) {
        return orderService.setOrderAsPaid(orderId);
    }
}
