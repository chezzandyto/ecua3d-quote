package com.ecua3d.quote.security.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

/**
 * Custom implementation of AuditorAware<T> to get the User Data
 * for Auditing operations
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Gets the username of the current auditor from Spring Security authentication.
     *
     * @return A Optional containing the username of the current auditor or an empty Optional if unauthenticated.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
            username = (String) ( token ).getTokenAttributes().get( "sub" );
            return Optional.of(username);
        }
        return Optional.empty();
    }
}
