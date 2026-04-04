package com.ms.finance_data_processing_service.services;

import com.ms.finance_data_processing_service.dtos.request.FinanceCreateRequestDto;
import com.ms.finance_data_processing_service.dtos.request.FinanceListRequestQueryDto;
import com.ms.finance_data_processing_service.dtos.request.FinanceUpdateRequestDto;
import com.ms.finance_data_processing_service.dtos.response.FinanceResponseDto;
import com.ms.finance_data_processing_service.entites.FinanceEntity;
import com.ms.finance_data_processing_service.entites.Types.FinanceCategoryType;
import com.ms.finance_data_processing_service.entites.Types.FinanceStatusType;
import com.ms.finance_data_processing_service.entites.Types.FinanceType;
import com.ms.finance_data_processing_service.exceptions.ApiError;
import com.ms.finance_data_processing_service.exceptions.ApiErrorException;
import com.ms.finance_data_processing_service.mappers.FinanceMapper;
import com.ms.finance_data_processing_service.repositories.FinanceRepository;
import com.ms.finance_data_processing_service.repositories.UserRepository;
import com.ms.finance_data_processing_service.utils.ConstantUtil;
import com.ms.finance_data_processing_service.utils.StartAndEndTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinanceRepository financeRepository;
    private final UserRepository userRepository;

    public FinanceResponseDto create(FinanceCreateRequestDto financeCreateBody, Long loggedInUserId) {
        FinanceEntity finance = FinanceMapper.toEntity(financeCreateBody);
        finance.setCreatedBy(userRepository.getReferenceById(loggedInUserId));
        financeRepository.save(finance);
        return FinanceMapper.toDto(finance);
    }

    public FinanceResponseDto details(Long id,boolean deleted) {
        FinanceEntity finance = financeRepository.findByIdAndIsDeleted(id,deleted)
                .orElseThrow(() -> new ApiErrorException(ApiError.DATA_NOT_FOUND));
        return FinanceMapper.toDto(finance);
    }

    @Transactional
    public void statusUpdate(Long id, String status, Long loggedInUserId) {
        int updateCount = financeRepository
                .statusUpdateById(
                        id,
                        FinanceStatusType.valueOf(status),
                        userRepository.getReferenceById(loggedInUserId)
                );
        if (updateCount == 0) {
            throw new ApiErrorException(ApiError.DATA_NOT_FOUND);
        }
        return;
    }

    @Transactional
    public FinanceResponseDto update(Long id, FinanceUpdateRequestDto financeUpdateBody, Long loggedInUserId) {
        FinanceEntity finance = financeRepository.findById(id)
                .orElseThrow(() -> new ApiErrorException(ApiError.DATA_NOT_FOUND));

        FinanceMapper.updateEntity(finance, financeUpdateBody);
        finance.setUpdatedBy(userRepository.getReferenceById(loggedInUserId));
        financeRepository.save(finance);
        return FinanceMapper.toDto(finance);
    }

    public Page<FinanceResponseDto> getList(FinanceListRequestQueryDto query) {
        List<Sort.Order> orders = ConstantUtil.getSortWithOrder(getAllowSortFields(), query.getSort());
        Sort sortOrder = orders == null || orders.isEmpty()
                ? Sort.unsorted()
                : Sort.by(orders);

        Long id = setIdInSearch(query);

        StartAndEndTimeUtil dateRange=ConstantUtil.getStatAndEndDateTime(query.getStartDate(),query.getEndDate());
        System.out.println(dateRange.endDateTime());
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getLimit(), sortOrder);
        Page<FinanceResponseDto> financeList = financeRepository.list(
                id,
                query.getType()==null?null:FinanceType.valueOf(query.getType()),
                query.getStatus()==null?null:FinanceStatusType.valueOf(query.getStatus()),
                query.getCategory()==null?null:FinanceCategoryType.valueOf(query.getCategory()),
                query.getCreatedBy(),
                query.getSearch(),
                query.getNote(),
                query.getDeleteData(),
                dateRange.startDateTime(),
                dateRange.endDateTime(),
                pageable
        );

        if (financeList.isEmpty()) {
            throw new ApiErrorException(ApiError.DATA_NOT_FOUND);
        }
        return financeList;
    }

    @Transactional
    public void delete(Long id,Long loggedInUserId) {
        int updateCount = financeRepository
                .deleteById(
                        id,
                        userRepository.getReferenceById(loggedInUserId)
                );
        if (updateCount == 0) {
            throw new ApiErrorException(ApiError.DATA_NOT_FOUND);
        }
        return;
    }

    private Map<String, String> getAllowSortFields() {
        return Map.of(
                "id", "id",
                "amount", "amount"
        );
    }

    private Long setIdInSearch(FinanceListRequestQueryDto query) {
        try {
            if (query.getSearch() == null || query.getSearch().isBlank()) {
                return null;
            }
            Long id = Long.valueOf(query.getSearch());
            query.setSearch(null);
            return id;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }


}
