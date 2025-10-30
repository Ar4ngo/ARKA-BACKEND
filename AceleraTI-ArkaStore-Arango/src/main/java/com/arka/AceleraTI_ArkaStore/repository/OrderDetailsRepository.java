package com.arka.AceleraTI_ArkaStore.repository;

import com.arka.AceleraTI_ArkaStore.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
