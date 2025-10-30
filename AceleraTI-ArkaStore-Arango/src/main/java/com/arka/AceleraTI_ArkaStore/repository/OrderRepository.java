package com.arka.AceleraTI_ArkaStore.repository;

import com.arka.AceleraTI_ArkaStore.model.Order;
import com.arka.AceleraTI_ArkaStore.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}
