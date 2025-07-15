package com.example.demo_json_and_gson.repository;

import com.example.demo_json_and_gson.entitys.Product;
import com.example.demo_json_and_gson.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal lower, BigDecimal upper);

    List<Product> findBySellerAndBuyerIsNotNull(User seller);
}
