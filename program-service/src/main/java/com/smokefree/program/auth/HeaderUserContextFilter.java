package com.smokefree.program.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class HeaderUserContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        try {
            String uid = req.getHeader("X-User-Id");
            String roles = req.getHeader("X-User-Roles");
            String tier = req.getHeader("X-User-Tier");
            String tz = req.getHeader("X-User-Timezone");
            String entState = req.getHeader("X-Ent-State");
            String entExp = req.getHeader("X-Ent-ExpiresAt");

            UserPrincipal principal = UserPrincipal.builder()
                    .userId(uid != null ? UUID.fromString(uid) : null)
                    .roles(roles == null ? Set.of() : new HashSet<>(Arrays.asList(roles.split(","))))
                    .tier(tier)
                    .timezone(tz)
                    .entState(entState)
                    .entExpiresAt(entExp == null ? null : Instant.parse(entExp))
                    .build();

            SecurityContextHolder.getContext().setAuthentication(principal);
        } catch (Exception ignored) { /* để anonymous nếu header sai */ }

        chain.doFilter(req, res);
    }
}
