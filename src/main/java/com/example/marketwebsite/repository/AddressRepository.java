package com.example.marketwebsite.repository;

import com.example.marketwebsite.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends JpaRepository<AddressEntity, Integer> {
}
