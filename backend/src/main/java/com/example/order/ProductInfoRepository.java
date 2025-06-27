package com.example.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    Optional<ProductInfo> findBySku(String sku);
}
