package com.example.FakeCommerce.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeCommerce.dtos.CreateOrderProductRequestDto;
import com.example.FakeCommerce.dtos.CreateOrderRequestDto;
import com.example.FakeCommerce.dtos.OrderProductResponseDto;
import com.example.FakeCommerce.dtos.OrderResponseDto;
import com.example.FakeCommerce.dtos.OrderSummaryResponseDto;
import com.example.FakeCommerce.dtos.UpdateOrderStatusRequestDto;
import com.example.FakeCommerce.repositories.OrderProductsRepository;
import com.example.FakeCommerce.repositories.OrderRepository;
import com.example.FakeCommerce.repositories.ProductRepository;
import com.example.FakeCommerce.schema.Order;
import com.example.FakeCommerce.schema.OrderProducts;
import com.example.FakeCommerce.schema.OrderStatus;
import com.example.FakeCommerce.schema.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final ProductRepository productRepository;

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    public OrderResponseDto getOrderById(Long id) {
        return toResponseDto(getOrder(id));
    }

    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    public OrderSummaryResponseDto getOrderSummary(Long id) {
        Order order = getOrder(id);
        List<OrderProducts> orderProducts = orderProductsRepository.findByOrderId(order.getId());

        int totalItems = orderProducts.stream()
                .mapToInt(OrderProducts::getQuantity)
                .sum();

        BigDecimal totalAmount = orderProducts.stream()
                .map(this::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderSummaryResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalItems(totalItems)
                .totalAmount(totalAmount)
                .build();
    }

    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        Order order = Order.builder()
                .userId(requestDto.getUserId())
                .status(requestDto.getStatus() == null ? OrderStatus.PENDING : requestDto.getStatus())
                .build();
        Order savedOrder = orderRepository.save(order);

        if (requestDto.getProducts() != null) {
            requestDto.getProducts().forEach(productRequest -> addProduct(savedOrder, productRequest));
        }

        return toResponseDto(savedOrder);
    }

    public OrderResponseDto updateOrderStatus(Long id, UpdateOrderStatusRequestDto requestDto) {
        if (requestDto.getStatus() == null) {
            throw new RuntimeException("status is required");
        }

        Order order = getOrder(id);
        order.setStatus(requestDto.getStatus());
        return toResponseDto(orderRepository.save(order));
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("order not found");
        }
        orderRepository.deleteById(id);
    }

    private Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("order not found"));
    }

    private void addProduct(Order order, CreateOrderProductRequestDto productRequest) {
        if (productRequest.getQuantity() == null || productRequest.getQuantity() <= 0) {
            throw new RuntimeException("quantity must be greater than zero");
        }

        Product product = productRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("product not found"));

        OrderProducts orderProducts = OrderProducts.builder()
                .order(order)
                .product(product)
                .quantity(productRequest.getQuantity())
                .build();
        orderProductsRepository.save(orderProducts);
    }

    private OrderResponseDto toResponseDto(Order order) {
        List<OrderProductResponseDto> products = orderProductsRepository.findByOrderId(order.getId()).stream()
                .map(this::toProductResponseDto)
                .toList();

        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .products(products)
                .build();
    }

    private OrderProductResponseDto toProductResponseDto(OrderProducts orderProducts) {
        Product product = orderProducts.getProduct();

        return OrderProductResponseDto.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .quantity(orderProducts.getQuantity())
                .build();
    }

    private BigDecimal getLineTotal(OrderProducts orderProducts) {
        return orderProducts.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(orderProducts.getQuantity()));
    }
}
