package com.ms.finance_data_processing_service.repositories;

import com.ms.finance_data_processing_service.entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

   Optional<UserEntity> findByEmail(String email);
}
