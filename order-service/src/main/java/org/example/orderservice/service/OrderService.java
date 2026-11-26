
package com.example.orderservice.service;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.CustomerResponse;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(
            OrderRepository orderRepository,
            RestTemplate restTemplate) {

        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Order createOrder(CreateOrderRequest request) {

        if (request.getCustomerId() == null
                || request.getCustomerId().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Customer ID is required");
        }

        if (request.getProductId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Product ID is required");
        }

        if (request.getQuantity() == null
                || request.getQuantity() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than zero");
        }

        // Check whether the customer exists.
        CustomerResponse customer = restTemplate.getForObject(
                "http://customer-service/customers/{id}",
                CustomerResponse.class,
                request.getCustomerId());

        if (customer == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer not found");
        }

        // Retrieve the product and its current price.
        ProductResponse product = restTemplate.getForObject(
                "http://product-service/products/{id}",
                ProductResponse.class,
                request.getProductId());

        if (product == null || product.getPrice() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found");
        }

        BigDecimal unitPrice = product.getPrice();

        BigDecimal totalPrice = unitPrice.multiply(
                BigDecimal.valueOf(request.getQuantity()));

        Order order = new Order();

        order.setCustomerId(customer.getId());
        order.setProductId(product.getId());
        order.setQuantity(request.getQuantity());
        order.setUnitPrice(unitPrice);
        order.setTotalPrice(totalPrice);
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order not found"));
    }
}
