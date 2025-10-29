package com.smokefree.program.auth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.*;

@Getter
@Builder
public class UserPrincipal implements Authentication {
    private final UUID userId;
    private final Set<String> roles;
    private final String tier;        // basic|premium|vip
    private final String timezone;    // ví dụ Asia/Ho_Chi_Minh
    private final String entState;    // TRIALING|ACTIVE|GRACE|EXPIRED
    private final Instant entExpiresAt;

    private boolean authenticated = true;

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return List.of();
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }
    @Override public Object getCredentials() { return null; }
    @Override public Object getDetails() { return null; }
    @Override public Object getPrincipal() { return userId; }
    @Override public boolean isAuthenticated() { return authenticated; }
    @Override public void setAuthenticated(boolean isAuthenticated) { this.authenticated = isAuthenticated; }
    @Override public String getName() { return userId != null ? userId.toString() : "anonymous"; }
}
