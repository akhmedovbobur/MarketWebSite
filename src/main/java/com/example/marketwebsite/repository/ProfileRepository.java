package com.example.marketwebsite.repository;

import com.example.marketwebsite.entity.ProfileEntity;
import com.example.marketwebsite.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer>, JpaSpecificationExecutor<ProfileEntity>{

    @Transactional
    @Modifying
    @Query("update ProductEntity set status=:status where id=:id")
    int changeStatus(@Param("status")ProfileStatus status, @Param("id") Integer id);


    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);

    Optional<ProfileEntity> findByEmail(String email);


}
