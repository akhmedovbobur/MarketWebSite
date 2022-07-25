package com.example.marketwebsite.repository;

import com.example.marketwebsite.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer>{

        Optional<ImageEntity> findByToken(String token);
}
