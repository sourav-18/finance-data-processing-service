package com.ms.finance_data_processing_service.exceptions;

import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(ApiError.INVALID_ACCESS.getStatusCode());
        String json = ApiResponseMapper.securityError(ApiError.INVALID_ACCESS.getStatusCode(),ApiError.INVALID_ACCESS.getMessage());
        response.getWriter().write(json);
    }
}
