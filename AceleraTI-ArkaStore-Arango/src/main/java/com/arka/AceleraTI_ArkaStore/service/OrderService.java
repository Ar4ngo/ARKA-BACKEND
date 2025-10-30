package com.arka.AceleraTI_ArkaStore.service;

import com.arka.AceleraTI_ArkaStore.dto.DetailOrderDto;
import com.arka.AceleraTI_ArkaStore.dto.OrderRequestDto;
import com.arka.AceleraTI_ArkaStore.model.*;
import com.arka.AceleraTI_ArkaStore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final NotificationService notificationService;

    public OrderService(OrderRepository orderRepository,
                        OrderDetailsRepository orderDetailsRepository,
                        ProductRepository productRepository,
                        StockHistoryRepository stockHistoryRepository,
                        NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
        this.stockHistoryRepository = stockHistoryRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Order createOrder(OrderRequestDto dto, Customer customer) {
        if (dto == null || dto.getItems() == null || dto.getItems().isEmpty())
            throw new IllegalArgumentException("Order must have at least one item");

        Order order = new Order();
        order.setCustomer(customer);

        List<OrderDetails> details = new ArrayList<>();
        double total = 0;

        for (DetailOrderDto item : dto.getItems()) {
            Product p = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id " + item.getProductId()));

            if (p.getStock() < item.getQuantity())
                throw new IllegalArgumentException("Insufficient stock for product " + p.getName());

            int previousStock = p.getStock();
            p.setStock(previousStock - item.getQuantity());
            productRepository.save(p);

            StockHistory sh = new StockHistory(p, previousStock, p.getStock(), "Order placed");
            stockHistoryRepository.save(sh);

            OrderDetails od = new OrderDetails(order, p, item.getQuantity(), p.getPrice());
            details.add(od);
            total += p.getPrice() * item.getQuantity();
        }

        order.setDetails(details);
        order.setTotal(total);
        Order saved = orderRepository.save(order);

        details.forEach(d -> d.setOrder(saved));
        orderDetailsRepository.saveAll(details);

        notificationService.sendOrderStatusChangeNotification(saved);
        return saved;
    }

    @Transactional
    public Order updateOrder(Long orderId, OrderRequestDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING)
            throw new IllegalArgumentException("Only pending orders can be modified");

        for (OrderDetails od : order.getDetails()) {
            Product p = od.getProduct();
            int previousStock = p.getStock();
            p.setStock(previousStock + od.getQuantity());
            productRepository.save(p);

            StockHistory sh = new StockHistory(p, previousStock, p.getStock(), "Order modified - items returned");
            stockHistoryRepository.save(sh);

            orderDetailsRepository.delete(od);
        }

        order.getDetails().clear();
        orderRepository.delete(order);

        return createOrder(dto, order.getCustomer());
    }

    @Transactional
    public Order changeStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        order.setStatus(status);
        Order saved = orderRepository.save(order);

        notificationService.sendOrderStatusChangeNotification(saved);
        return saved;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }
}
