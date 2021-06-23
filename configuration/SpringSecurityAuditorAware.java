package com.Bootcamp.Project.Application.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override

    public Optional<String> getCurrentAuditor() {
        String name = "Admin";
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return Optional.ofNullable(name);
    }
}
