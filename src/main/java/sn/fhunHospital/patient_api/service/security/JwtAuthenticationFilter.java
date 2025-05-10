package sn.fhunHospital.patient_api.service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.fhunHospital.patient_api.config.security.JwtProperties;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   JwtProperties jwtProperties,
                                   UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(jwtProperties.getHeaderName());

        if (authHeader == null || !authHeader.startsWith(jwtProperties.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = "";
        if (authHeader != null && authHeader.startsWith(jwtProperties.getTokenPrefix())) {
            token = authHeader.substring(jwtProperties.getTokenPrefix().length());
        }

        if (jwtService.validateToken(token)) {
            String username = jwtService.getUsernameFromToken(token);
            List<GrantedAuthority> authorities = jwtService.getAuthoritiesFromToken(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
