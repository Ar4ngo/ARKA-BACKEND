package com.arka.AceleraTI_ArkaStore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(nullable = false)
    private Double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> details = new ArrayList<>();

    public Order() {
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.total = 0.0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public List<OrderDetails> getDetails() { return details; }
    public void setDetails(List<OrderDetails> details) { this.details = details; }

    public void addDetail(OrderDetails detail) {
        details.add(detail);
        detail.setOrder(this);
    }

    public void removeDetail(OrderDetails detail) {
        details.remove(detail);
        detail.setOrder(null);
    }
}
