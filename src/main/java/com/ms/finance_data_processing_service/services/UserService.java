package com.ms.finance_data_processing_service.services;

import com.ms.finance_data_processing_service.dtos.request.UserCreateRequestDto;
import com.ms.finance_data_processing_service.dtos.request.UserListRequestQueryDto;
import com.ms.finance_data_processing_service.dtos.request.UserUpdateRequestDto;
import com.ms.finance_data_processing_service.dtos.response.UserResponseDto;
import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import com.ms.finance_data_processing_service.entites.UserEntity;
import com.ms.finance_data_processing_service.exceptions.ApiError;
import com.ms.finance_data_processing_service.exceptions.ApiErrorException;
import com.ms.finance_data_processing_service.mappers.UserMapper;
import com.ms.finance_data_processing_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto create(UserCreateRequestDto userCreateBody, Long LoggedInUserId) {
        UserEntity isUserExist = userRepository.findByEmail(userCreateBody.getEmail()).orElse(null);

        if (isUserExist != null) {
            log.info("Duplicate Email id: {}", userCreateBody.getEmail());
            throw new ApiErrorException(ApiError.DUPLICATE_USER);
        }

        UserEntity newUser = UserMapper.toEntity(userCreateBody);
        newUser.setPassword(passwordEncoder.encode(userCreateBody.getPassword()));
        newUser.setCreatedBy(userRepository.getReferenceById(LoggedInUserId));
        userRepository.save(newUser);
        return UserMapper.toResponseDto(newUser);
    }

    public UserResponseDto getProfileDetails(Long loggedInUserId) {
        UserEntity user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new ApiErrorException(ApiError.USER_NOT_FOUND));
        return UserMapper.toResponseDto(user);
    }

    public Page<UserResponseDto> getList(UserListRequestQueryDto query) {
        Sort sort = Integer.valueOf(1).equals(query.getSort()) ?
                Sort.by("id").ascending() :
                Sort.by("id").descending();

        Long id = setIdInSearch(query);

        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getLimit(), sort);

        Page<UserResponseDto> userList = userRepository.list(
                id,
                query.getSearch(),
                query.getRole(),
                query.getStatus(),
                pageable);
        if (userList.isEmpty()) {
            throw new ApiErrorException(ApiError.DATA_NOT_FOUND);
        }

        return userList;
    }

    @Transactional
    public void statusUpdate(Long userId, UserStatusType status, Long loggedInUserId) {
        int updateCount = userRepository
                .statusUpdateById(
                        userId,
                        status,
                        userRepository.getReferenceById(loggedInUserId)
                );
        if (updateCount == 0) {
            throw new ApiErrorException(ApiError.USER_NOT_FOUND);
        }
        return;
    }

    @Transactional
    public void roleUpdate(Long userId, UserRoleType role, Long loggedInUserId) {
        int updateCount = userRepository
                .roleUpdateById(
                        userId,
                        role,
                        userRepository.getReferenceById(loggedInUserId)
                );
        if (updateCount == 0) {
            throw new ApiErrorException(ApiError.USER_NOT_FOUND);
        }
        return;
    }

    @Transactional
    public UserResponseDto update(Long userId, UserUpdateRequestDto updateBody, Long loggedInUserId) {
        Long emailTakenUserId = userRepository.isEmailTakenByOther(userId, updateBody.getEmail());
        if (emailTakenUserId != null) {
            log.info("Duplicate Email id in update: {}", updateBody.getEmail());
            throw new ApiErrorException(ApiError.DUPLICATE_USER);
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiErrorException(ApiError.USER_NOT_FOUND));
        UserMapper.updateEntity(user, updateBody);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUpdatedBy(userRepository.getReferenceById(loggedInUserId));
        userRepository.save(user);
        return UserMapper.toResponseDto(user);
    }

    private Long setIdInSearch(UserListRequestQueryDto query) {
        try {
            if (query.getSearch() == null || query.getSearch().isBlank()) {
                return null;
            }
            Long id= Long.valueOf(query.getSearch());
            query.setSearch(null);
            return id;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
