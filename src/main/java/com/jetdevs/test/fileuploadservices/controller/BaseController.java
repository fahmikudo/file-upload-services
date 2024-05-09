package com.jetdevs.test.fileuploadservices.controller;

import com.jetdevs.test.fileuploadservices.entity.User;
import com.jetdevs.test.fileuploadservices.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public abstract class BaseController {

    private final AuthenticationService authenticationService;

    protected User getUserActiveFromContext() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticationService.getCurrentUser(principal);
    }

}
