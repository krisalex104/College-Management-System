package com.example.collegemanagementsystem.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //        get security context
//        get authentication
//        get the principle
//        get the username
        return Optional.of("Krishna Kumar Pandey");
    }
}
