package com.ms.finance_data_processing_service.filters;

import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import com.ms.finance_data_processing_service.entites.UserEntity;
import com.ms.finance_data_processing_service.exceptions.ApiError;
import com.ms.finance_data_processing_service.exceptions.ApiErrorException;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.repositories.UserRepository;
import com.ms.finance_data_processing_service.services.JwtService;
import com.ms.finance_data_processing_service.utils.ConstantUtil;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yaml.snakeyaml.util.EnumUtils;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().startsWith(UrlUtil.AUTH_URL)){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String authHeader = request.getHeader("Authorization");
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                token = authHeader.substring(7);
            }

            if (token == null || token.isEmpty()) {
                throw new ApiErrorException(ApiError.TOKEN_MISSING);
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                if (!jwtService.isTokenExpired(token)) {
                    Claims claims = jwtService.verifySignatureAdnExtractAllClaims(token);

                    UserEntity user=userRepository.findByIdAndStatus(Long.valueOf(claims.getSubject()), UserStatusType.Active)
                            .orElseThrow(() -> new ApiErrorException(ApiError.INACTIVE_USER));

                    request.setAttribute(ConstantUtil.REQUEST_USER_ID,user.getId());

                    List<SimpleGrantedAuthority> simpleGrantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, simpleGrantedAuthorities);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ApiErrorException ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(ex.getError().getStatusCode());
            response.setContentType("application/json");
            response.getWriter().write(
                    ApiResponseMapper.securityError(ex.getError().getStatusCode(),ex.getError().getMessage())
            );
        }
    }
}
