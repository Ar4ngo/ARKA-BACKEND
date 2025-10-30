package com.arka.AceleraTI_ArkaStore.repository;

import com.arka.AceleraTI_ArkaStore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCheckedOutFalseAndCreatedAtBefore(LocalDateTime cutoff);
}
