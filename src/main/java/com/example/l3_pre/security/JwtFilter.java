package com.example.l3_pre.security;

import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.l3_pre.consts.NameConst.AUTH_TYPE;
import static com.example.l3_pre.consts.NameConst.HEADER_AUTHORIZATION;
import static com.example.l3_pre.consts.ValueConst.BEARER_NAME_LENGTH;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            authenticateRequest(request);
        } catch (Exception exception) {
            response.setStatus(ErrorMessageConstant.UNAUTHORIZED.getCode());
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateRequest(HttpServletRequest request) {
        String token = solveToken(request);
        if (!ObjectUtils.isEmpty(token) && jwtService.validateToken(token)) {
            Authentication authentication = jwtService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private static String solveToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER_AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(AUTH_TYPE)) {
            return authHeader.substring(BEARER_NAME_LENGTH);
        }
        return null;
    }
}
