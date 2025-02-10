package com.bank.orders_service.service;

import com.bank.orders_service.dto.CreateOrderDTO;
import com.bank.orders_service.dto.CreateOrderItemDTO;
import com.bank.orders_service.dto.OrderResponseDTO;
import com.bank.orders_service.model.Order;

import java.util.List;

public interface OrderService {
    OrderResponseDTO createOrder(CreateOrderDTO orderDTO);

    OrderResponseDTO getOrderById(Long orderId);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO cancelOrder(Long orderId);

    OrderResponseDTO addOrderItem(CreateOrderItemDTO createOrderItemDTO, Long orderId);

    OrderResponseDTO removeOrderItemByProduct(Long orderId, Long productId);

    OrderResponseDTO setOrderAsPaid(Long orderId);

    /*void payOrder(Long id);*/
}
