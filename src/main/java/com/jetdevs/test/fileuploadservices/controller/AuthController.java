package com.jetdevs.test.fileuploadservices.controller;

import com.jetdevs.test.fileuploadservices.model.AuthRequest;
import com.jetdevs.test.fileuploadservices.model.AuthResponse;
import com.jetdevs.test.fileuploadservices.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authenticationService.login(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
