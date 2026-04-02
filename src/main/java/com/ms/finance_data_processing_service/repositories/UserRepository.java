package com.ms.finance_data_processing_service.repositories;

import com.ms.finance_data_processing_service.dtos.response.UserResponseDto;
import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import com.ms.finance_data_processing_service.entites.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

   Optional<UserEntity> findByEmail(String email);
   Optional<UserEntity> findByIdAndStatus(Long id, UserStatusType status);

   @Modifying
   @Query("UPDATE UserEntity SET status=:status, updatedBy=:updatedBy WHERE id=:id")
   int statusUpdateById(@Param("id") Long id,
                        @Param("status") UserStatusType status,
                        @Param("updatedBy") UserEntity updatedBy);

   @Modifying
   @Query("UPDATE UserEntity SET role=:role, updatedBy=:updatedBy WHERE id=:id")
   int roleUpdateById(@Param("id") Long id,
                        @Param("role") UserRoleType role,
                        @Param("updatedBy") UserEntity updatedBy);

   @Query("SELECT id FROM UserEntity WHERE id!=:id AND email=:email")
   Long isEmailTakenByOther(
           @Param("id") Long id,
           @Param("email") String email
   );

   @Query("""
    SELECT new com.ms.finance_data_processing_service.dtos.response.UserResponseDto(
    U.id,
    U.name,
    U.email,
    U.status,
    U.role,
    U.profilePicture,
    CB.name,
    UB.name,
    U.createdAt
    )
    FROM UserEntity U
    LEFT JOIN U.createdBy CB
    LEFT JOIN U.updatedBy UB
    WHERE (:role IS NULL OR U.role=:role)
    AND (:status IS NULL OR U.status=:status)
    AND (:search IS NULL OR U.name iLIKE %:search% OR U.email iLIKE %:search%)
    AND (:id IS NULL OR U.id=:id)
""")
   Page<UserResponseDto> list(
                              @Param("id") Long id,
                              @Param("search") String search,
                              @Param("role") UserRoleType role,
                              @Param("status") UserStatusType status,
                              Pageable pageable);


}
