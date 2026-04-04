package com.ms.finance_data_processing_service.controllers;


import com.ms.finance_data_processing_service.dtos.request.*;
import com.ms.finance_data_processing_service.dtos.response.ApiPageResponseDto;
import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import com.ms.finance_data_processing_service.dtos.response.FinanceResponseDto;
import com.ms.finance_data_processing_service.dtos.response.UserResponseDto;
import com.ms.finance_data_processing_service.dtos.validations.ValidEnum;
import com.ms.finance_data_processing_service.entites.FinanceEntity;
import com.ms.finance_data_processing_service.entites.Types.FinanceStatusType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.services.FinanceService;
import com.ms.finance_data_processing_service.utils.ConstantUtil;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping(UrlUtil.FINANCE_URL)
@RequiredArgsConstructor
@Validated
public class FinanceController {

    private final FinanceService financeService;

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponseDto<FinanceResponseDto>> create(HttpServletRequest request,
                                                                     @Valid @RequestBody FinanceCreateRequestDto financeCreateBody) {
        Long loginUserId = (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        FinanceResponseDto apiResponse = financeService.create(financeCreateBody,loginUserId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseMapper.success(HttpStatus.CREATED.value(), "Finance record add successfully", apiResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<FinanceResponseDto>> details(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean deleted
        ){
        System.out.println(request.getRemoteAddr());
        FinanceResponseDto apiResponse = financeService.details(id,deleted);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(), "Finance details fetch successfully", apiResponse));
    }

    @PreAuthorize("hasRole('Admin')")
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<ApiResponseDto<Void>> statusUpdate(HttpServletRequest request, @PathVariable Long id,
             @ValidEnum(enumClass = FinanceStatusType.class,field = "status") @PathVariable String status){
        Long loggedInUserId= (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        financeService.statusUpdate(id,status,loggedInUserId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),"Finance status update successfully",null));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponseDto<FinanceResponseDto>>  update(
            HttpServletRequest request,
            @PathVariable Long id,
            @Valid @RequestBody FinanceUpdateRequestDto financeUpdateBody
    ){
        Long loggedInUserId= (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        FinanceResponseDto apiResponse= financeService.update(id,financeUpdateBody,loggedInUserId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),"Finance update successfully",apiResponse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponseDto<Void>>  delete(
            HttpServletRequest request,
            @PathVariable Long id
    ){
        Long loggedInUserId= (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        financeService.delete(id,loggedInUserId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),"Finance delete successfully",null));
    }

    @GetMapping
    public ResponseEntity<ApiPageResponseDto<FinanceResponseDto>>  list(
            @ModelAttribute @Valid  FinanceListRequestQueryDto query
    ){
        Page<FinanceResponseDto> financeList= financeService.getList(query);
        ApiPageResponseDto<FinanceResponseDto> apiResponse=ApiResponseMapper.successWithPagination(
                HttpStatus.OK.value(),
                "Finance list fetch successfully",
                financeList.getContent(),
                financeList.getNumber()+1,
                financeList.getSize(),
                financeList.getTotalPages(),
                financeList.getTotalElements()
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }






}
