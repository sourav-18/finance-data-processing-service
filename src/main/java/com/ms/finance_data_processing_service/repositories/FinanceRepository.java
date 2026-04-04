package com.ms.finance_data_processing_service.repositories;

import com.ms.finance_data_processing_service.dtos.response.*;
import com.ms.finance_data_processing_service.entites.FinanceEntity;
import com.ms.finance_data_processing_service.entites.Types.*;
import com.ms.finance_data_processing_service.entites.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FinanceRepository extends JpaRepository<FinanceEntity,Long> {

    @Modifying
    @Query("UPDATE FinanceEntity SET status=:status, updatedBy=:updatedBy WHERE id=:id")
    int statusUpdateById(@Param("id") Long id,
                         @Param("status") FinanceStatusType status,
                         @Param("updatedBy") UserEntity updatedBy);

    @Modifying
    @Query("UPDATE FinanceEntity SET isDeleted=true, updatedBy=:updatedBy WHERE id=:id AND isDeleted=false")
    int deleteById(@Param("id") Long id,
                         @Param("updatedBy") UserEntity updatedBy);


    @Query("SELECT F FROM FinanceEntity F WHERE id=:id AND isDeleted=:deleted")
    Optional<FinanceEntity> findByIdAndIsDeleted(@Param("id") Long id,
                                       @Param("deleted") boolean deleted);

    @Query("""
    SELECT new com.ms.finance_data_processing_service.dtos.response.FinanceResponseDto(
    F.id,
    F.amount,
    F.type,
    F.status,
    F.category,
    F.note,
    CB.name,
    UB.name,
    F.createdAt,
    F.updatedAt
    )
    FROM FinanceEntity F
    LEFT JOIN F.createdBy CB
    LEFT JOIN F.updatedBy UB
    WHERE F.isDeleted=:deleted
    AND (:type IS NULL OR F.type=:type)
    AND (:status IS NULL OR F.status=:status)
    AND (:category IS NULL OR F.category=:category)
    AND (:search IS NULL OR F.note iLIKE %:search%)
    AND (:createdBy IS NULL OR CB.id=:createdBy)
    AND (F.createdAt >= COALESCE(:startDate, F.createdAt))
    AND (F.createdAt <= COALESCE(:endDate, F.createdAt))
    AND (:id IS NULL OR F.id=:id)
""")
    Page<FinanceResponseDto> list(
            @Param("id") Long id,
            @Param("type") FinanceType type,
            @Param("status") FinanceStatusType status,
            @Param("category") FinanceCategoryType category,
            @Param("createdBy") Long createdBy,
            @Param("search") String search,
            @Param("note") String note,
            @Param("deleted") boolean deleted,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("""
            SELECT COALESCE(SUM(F.amount), 0)
            FROM FinanceEntity F
            WHERE F.isDeleted = false
            AND F.type = :type
            AND (F.createdAt >= COALESCE(:startDate, F.createdAt))
            AND (F.createdAt <= COALESCE(:endDate, F.createdAt))
""")
    Long getTypeAmount(
            @Param("type") FinanceType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
            SELECT
            new com.ms.finance_data_processing_service.dtos.response.CategoryAmountResponseDto(
            SUM(F.amount) AS amount,
            F.category AS category)
            FROM FinanceEntity F
            WHERE F.isDeleted = false
            AND (F.createdAt >= COALESCE(:startDate, F.createdAt))
            AND (F.createdAt <= COALESCE(:endDate, F.createdAt))
            GROUP BY F.category
""")
    List<CategoryAmountResponseDto> getCategoryAmount(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
            SELECT new com.ms.finance_data_processing_service.dtos.response.BalanceByMonthResponseDto(
            SUM (CASE WHEN F.type = com.ms.finance_data_processing_service.entites.Types.FinanceType.Income THEN amount ELSE 0 END) as income,
            SUM(CASE WHEN F.type = com.ms.finance_data_processing_service.entites.Types.FinanceType.Expense THEN amount ELSE 0 END) as expense,
            DATE_TRUNC('MONTH',F.createdAt) as dateTime
            )
            FROM FinanceEntity F
            WHERE F.isDeleted = false
            AND F.createdAt >= :statDate
            AND F.createdAt < :endDate
            GROUP BY DATE_TRUNC('MONTH',createdAt)
            """)
    List<BalanceByMonthResponseDto> getBalanceByMonth(
            @Param("statDate") LocalDateTime starYear,
            @Param("endDate") LocalDateTime endYear
    );
    @Query("""
            SELECT new com.ms.finance_data_processing_service.dtos.response.BalanceByWeekResponseDto(
            SUM(CASE WHEN F.type = com.ms.finance_data_processing_service.entites.Types.FinanceType.Income THEN amount ELSE 0 END) as income,
            SUM(CASE WHEN F.type = com.ms.finance_data_processing_service.entites.Types.FinanceType.Expense THEN amount ELSE 0 END) as expense
            )
            FROM FinanceEntity F
            WHERE F.isDeleted = false
            AND F.createdAt >= :statDate
            AND F.createdAt < :endDate
            """)
    BalanceByWeekResponseDto getBalanceByWeek(
            @Param("statDate") LocalDateTime starYear,
            @Param("endDate") LocalDateTime endYear
    );
}
