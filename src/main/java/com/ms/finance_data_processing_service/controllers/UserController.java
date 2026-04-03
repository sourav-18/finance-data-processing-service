package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.dtos.request.LoginRequestDto;
import com.ms.finance_data_processing_service.dtos.request.UserCreateRequestDto;
import com.ms.finance_data_processing_service.dtos.request.UserListRequestQueryDto;
import com.ms.finance_data_processing_service.dtos.request.UserUpdateRequestDto;
import com.ms.finance_data_processing_service.dtos.response.ApiPageResponseDto;
import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import com.ms.finance_data_processing_service.dtos.response.LoginResponseDto;
import com.ms.finance_data_processing_service.dtos.response.UserResponseDto;
import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.services.UserService;
import com.ms.finance_data_processing_service.utils.ConstantUtil;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlUtil.USER_URL)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public ResponseEntity<ApiResponseDto<UserResponseDto>> create(HttpServletRequest request, @Valid @RequestBody UserCreateRequestDto userCreateBody){
        Long loggedInUserId= (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        UserResponseDto apiResponse=userService.create(userCreateBody,loggedInUserId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseMapper.success(HttpStatus.CREATED.value(),"User create successfully",apiResponse));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponseDto<UserResponseDto>>  getProfileDetails(HttpServletRequest request){
        Long loggedInUserId= (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        UserResponseDto apiResponse= userService.getProfileDetails(loggedInUserId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),"Profile details fetch successfully",apiResponse));
    }

    @PreAuthorize("hasRole('Admin')")
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<ApiResponseDto<Void>> statusUpdate(HttpServletRequest request, @PathVariable Long id, @PathVariable UserStatusType status){
        Long loggedInUserId= (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        userService.statusUpdate(id,status,loggedInUserId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),"User status update successfully",null));
    }

    @PreAuthorize("hasRole('Admin')")
    @PatchMapping("/{id}/role/{role}")
    public ResponseEntity<ApiResponseDto<Void>> roleUpdate(HttpServletRequest request, @PathVariable Long id, @PathVariable UserRoleType role){
        Long loggedInUserId= (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        userService.roleUpdate(id,role,loggedInUserId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),"User role update successfully",null));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponseDto<UserResponseDto>>  update(
            HttpServletRequest request,
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDto userUpdateBody
            ){
        Long loggedInUserId= (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        UserResponseDto apiResponse= userService.update(id,userUpdateBody,loggedInUserId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),"Profile details update successfully",apiResponse));
    }

    @GetMapping
    public ResponseEntity<ApiPageResponseDto<UserResponseDto>>  list(
           @Valid @ModelAttribute UserListRequestQueryDto query
            ){
        Page<UserResponseDto> userList= userService.getList(query);
        ApiPageResponseDto<UserResponseDto> apiResponse=ApiResponseMapper.successWithPagination(
                HttpStatus.OK.value(),
                "User list fetch successfully",
                userList.getContent(),
                userList.getNumber()+1,
                userList.getSize(),
                userList.getTotalPages(),
                userList.getTotalElements()
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }





}
