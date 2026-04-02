package com.ms.finance_data_processing_service.repositories;

import com.ms.finance_data_processing_service.entites.FinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinanceRepository extends JpaRepository<FinanceEntity,Long> {

}
