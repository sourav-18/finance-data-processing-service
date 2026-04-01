package com.ms.finance_data_processing_service.services;


import com.ms.finance_data_processing_service.dtos.request.LoginRequestDto;
import com.ms.finance_data_processing_service.entites.UserEntity;
import com.ms.finance_data_processing_service.exceptions.UserNotFoundException;
import com.ms.finance_data_processing_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Object login(LoginRequestDto loginBody) {
        UserEntity user = userRepository.findByEmail(loginBody.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user or password not match"));
        if(!passwordEncoder.matches(loginBody.getPassword(),user.getPassword())){
            throw new RuntimeException("user or password not match");
        }
        return user;
    }

}
