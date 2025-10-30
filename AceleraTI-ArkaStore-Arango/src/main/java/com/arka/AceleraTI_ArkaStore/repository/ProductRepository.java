package com.arka.AceleraTI_ArkaStore.repository;

import com.arka.AceleraTI_ArkaStore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockLessThan(Integer threshold);
}
