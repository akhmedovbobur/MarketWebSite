package com.example.marketwebsite.repository;

import com.example.marketwebsite.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Integer> {

    List<ProductImageEntity> findAllByProductId(Integer productId);
}
