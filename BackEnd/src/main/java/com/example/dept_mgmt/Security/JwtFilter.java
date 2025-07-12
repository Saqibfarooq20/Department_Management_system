package com.example.dept_mgmt.Security;

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
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (!jwtUtil.isValidToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or Expired Token");
                System.out.println("⛔ Invalid or expired token on URI: " + requestURI);
                return;
            }

            String email = jwtUtil.extractEmail(token);
            List<String> roles = jwtUtil.extractAuthorities(token);

            // Convert roles (like "ROLE_SUPER_ADMIN") into Spring authorities
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            var authToken = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
            request.setAttribute("email", email);

            // ✅ Debug Logs
            System.out.println("✅ JwtFilter - Authenticated request");
            System.out.println("   URI: " + requestURI);
            System.out.println("   User: " + email);
            System.out.println("   Authorities: " + authorities);
        } else {
            System.out.println("⚠️ JwtFilter - No Authorization header for URI: " + requestURI);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/login") ||
                path.equals("/api/addUser") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui");
    }
}
