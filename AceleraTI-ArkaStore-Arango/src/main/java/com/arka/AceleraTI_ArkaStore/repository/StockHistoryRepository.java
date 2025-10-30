package com.arka.AceleraTI_ArkaStore.repository;

import com.arka.AceleraTI_ArkaStore.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByProductId(Long productId);
}
