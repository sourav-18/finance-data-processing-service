package com.ms.finance_data_processing_service.filters;

import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.services.RateLimiterService;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if( request.getRequestURI().startsWith("/v3/api-docs")
           ||request.getRequestURI().startsWith("/swagger-ui/")){
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("remoter arr "+request.getRemoteAddr());

        boolean isAllow=rateLimiterService.isUserHaveToken(request.getRemoteAddr());
        if(isAllow){
            filterChain.doFilter(request,response);
        }else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write(ApiResponseMapper.securityError
                                (HttpStatus.TOO_MANY_REQUESTS.value(),
                                "Too many request"));
        }
    }
}
