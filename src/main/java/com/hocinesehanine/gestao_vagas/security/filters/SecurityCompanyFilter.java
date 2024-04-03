package com.hocinesehanine.gestao_vagas.security.filters;

import com.hocinesehanine.gestao_vagas.providers.JWTCompanyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityCompanyFilter extends OncePerRequestFilter {

    private final JWTCompanyProvider jwtCompanyProvider;

    public SecurityCompanyFilter(JWTCompanyProvider jwtCompanyProvider) {
        this.jwtCompanyProvider = jwtCompanyProvider;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/company") && (header != null)) {
            final var token = jwtCompanyProvider.validateToken(header);
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            final var tokenSubject = token.getSubject();
            request.setAttribute("company_id", tokenSubject);
            final var roles = token.getClaim("roles").asList(Object.class);
            final var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();
            final var auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
