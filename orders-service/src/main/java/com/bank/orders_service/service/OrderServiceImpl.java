package com.bank.orders_service.service;

import com.bank.orders_service.client.ProductClient;
import com.bank.orders_service.dto.*;
import com.bank.orders_service.exception.OrderCanceledException;
import com.bank.orders_service.exception.OrderItemsNotFoundException;
import com.bank.orders_service.exception.OrderNotFoundException;
import com.bank.orders_service.exception.ProductNotFoundException;
import com.bank.orders_service.model.Order;
import com.bank.orders_service.model.OrderItem;
import com.bank.orders_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDTO createOrder(CreateOrderDTO createOrderDTO) {

        // Validate if the order has items
        if (createOrderDTO.getItems() == null || createOrderDTO.getItems().isEmpty()) {
            throw new OrderItemsNotFoundException("Order must contain at least one item.");
        }

        // Create order
        Order order = new Order();

        BigDecimal orderTotalPrice = BigDecimal.ZERO;

        // Process each item of the order
        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderItemDTO createOrderItemDTO : createOrderDTO.getItems()) {

            OrderItem orderItem = createOrderItem(createOrderItemDTO, order);
            orderItems.add(orderItem);

            orderTotalPrice = orderTotalPrice.add(calculateOrderItemTotalPrice(orderItem));
        }

        // Set order values
        order.setCustomerName(createOrderDTO.getCustomerName());
        order.setTotalPrice(orderTotalPrice);
        order.setItems(orderItems);

        // Save order in database
        Order savedOrder = orderRepository.save(order);

        return convertToOrderResponseDTO(savedOrder);
    }

    // Get order by id
    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("The order with ID " + orderId + " not found"));

        return convertToOrderResponseDTO(order);
    }

    // Get all orders
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    // Add order item
    @Override
    public OrderResponseDTO addOrderItem(CreateOrderItemDTO createOrderItemDTO, Long orderId){

        // Validate if the order exists
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("The order with ID " + orderId + " not found"));

        // Validate if the order isn't canceled or paid
        ValidateOrderCanceledAndPaidStatus(order);

        // Convert DTO to order item model
        OrderItem orderItem = createOrderItem(createOrderItemDTO, order);

        // Validate if the product already exists in the items
        boolean itemAlreadyExists = false;
        for(OrderItem existingItem: order.getItems()){
            if (existingItem.getProductId().equals(orderItem.getProductId())) {
                existingItem.setQuantity(existingItem.getQuantity() + orderItem.getQuantity());
                existingItem.setPrice(orderItem.getPrice());
                itemAlreadyExists = true;
                break;
            }
        }

        // If item doesn't exist is added to the order
        if(!itemAlreadyExists){

            // Add order item
            order.getItems().add(orderItem);
        }

        // Update order total price
        BigDecimal orderTotalPrice = order.getItems().stream()
                .map(this::calculateOrderItemTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(orderTotalPrice);



        // Save order
        orderRepository.save(order);
        return convertToOrderResponseDTO(order);

    }

    // Remove an item from the order by product
    @Override
    public OrderResponseDTO removeOrderItemByProduct(Long orderId, Long productId){

        // Validate if order exists
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("The order with ID " + orderId + " not found"));

        // Validate if the order isn't canceled or paid
        ValidateOrderCanceledAndPaidStatus(order);

        // Remove items by product
        boolean itemRemoved = order.getItems().removeIf(orderItem -> orderItem.getProductId().equals(productId));

        if(!itemRemoved){
            throw new ProductNotFoundException(
                    "The product with ID: " + productId + " not found in the order"
            );
        }

        // Update order total price
        BigDecimal orderTotalPrice = order.getItems().stream()
                .map(this::calculateOrderItemTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(orderTotalPrice);

        // Save the order
        orderRepository.save(order);

        return convertToOrderResponseDTO(order);
    }

    // Cancel an order
    @Override
    public OrderResponseDTO cancelOrder(Long orderId){

        //Validate if the order exists
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("The order with ID " + orderId + " not found"));

        // Validate if the order isn't canceled or paid
        ValidateOrderCanceledAndPaidStatus(order);

        // Cancel order and save
        order.setIsCanceled(true);
        orderRepository.save(order);

        return convertToOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO setOrderAsPaid(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("The order with ID " + orderId + " not found"));

        // Validate if the order isn't canceled or paid
        ValidateOrderCanceledAndPaidStatus(order);

        // Update order payment status and save
        order.setIsPaid(true);
        orderRepository.save(order);

        return convertToOrderResponseDTO(order);
    }

    //Create order item from CreateOrderItemDTO
    private OrderItem createOrderItem(CreateOrderItemDTO createOrderItemDTO, Order order) {

        // Validate if product exists
        ValidateProduct(createOrderItemDTO.getProductId());

        // Create order item
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProductId(createOrderItemDTO.getProductId());
        orderItem.setPrice(createOrderItemDTO.getPrice());
        orderItem.setQuantity(createOrderItemDTO.getQuantity());

        return orderItem;
    }

    // Covert order model to orderResponseDTO
    private OrderResponseDTO convertToOrderResponseDTO(Order order) {


        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setCustomerName(order.getCustomerName());
        orderResponseDTO.setTotalPrice(order.getTotalPrice());
        orderResponseDTO.setIsPaid(order.getIsPaid());
        orderResponseDTO.setIsCanceled(order.getIsCanceled());

        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream()
                .map(this::convertToOrderItemResponseDTO)
                .collect(Collectors.toList());
        orderResponseDTO.setItems(itemDTOs);

        return orderResponseDTO;
    }

    // Convert order item model to order item response DTO
    private OrderItemResponseDTO convertToOrderItemResponseDTO(OrderItem item) {

        Optional<ProductDTO> productDTO = productClient.getProductById(item.getProductId());

        if (productDTO.isEmpty()) {
            throw new ProductNotFoundException(
                    "The product with ID: " + item.getProductId() + " not found"
            );
        }

        OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
        itemDTO.setId(item.getId());
        itemDTO.setProductId(item.getProductId());
        itemDTO.setProductTitle(productDTO.get().getTitle());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setQuantity(item.getQuantity());
        return itemDTO;
    }

    //Validate if product exists
    private void ValidateProduct(Long productId){
        productClient.getProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "The product with ID: " + productId + " not found"
                ));
    }

    private void ValidateOrderCanceledAndPaidStatus(Order order){
        if(order.getIsCanceled()){
            throw new OrderCanceledException(
                    "The order with ID " + order.getId() + " has been canceled and cannot be modified."
            );
        }

        if(order.getIsPaid()){
            throw new OrderCanceledException(
                    "The order with ID " + order.getId() + " has been paid"
            );
        }
    }

    private BigDecimal calculateOrderItemTotalPrice(OrderItem orderItem) {
        return orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
    }
}
