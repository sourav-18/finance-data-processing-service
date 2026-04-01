package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.dtos.request.LoginRequestDto;
import com.ms.finance_data_processing_service.services.AuthService;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlUtil.AUTH_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequestDto loginBody){
       return  authService.login(loginBody);
    }
}
