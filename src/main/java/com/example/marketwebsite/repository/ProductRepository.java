package com.example.marketwebsite.repository;

import com.example.marketwebsite.entity.ProductEntity;
import com.example.marketwebsite.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {

    @Transactional
    @Modifying
    @Query("update ProductEntity  set status=:status where id=:id")
    void changeStatus(Integer id, ProductStatus status);

}
