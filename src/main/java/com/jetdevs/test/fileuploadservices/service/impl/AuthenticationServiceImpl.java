package com.jetdevs.test.fileuploadservices.service.impl;

import com.jetdevs.test.fileuploadservices.entity.User;
import com.jetdevs.test.fileuploadservices.model.AuthRequest;
import com.jetdevs.test.fileuploadservices.model.AuthResponse;
import com.jetdevs.test.fileuploadservices.repository.UserRepository;
import com.jetdevs.test.fileuploadservices.security.JwtUtil;
import com.jetdevs.test.fileuploadservices.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(AuthRequest authRequest) {

        if (authRequest.getUsername() == null || authRequest.getPassword() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password does not match.");

        Optional<User> user = userRepository.findByUsername(authRequest.getUsername());
        if (user.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found.");

        boolean passwordRequest = passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword());
        if (!passwordRequest)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password does not match.");

        return AuthResponse.builder()
                .token(jwtUtil.generateToken(user.get()))
                .username(user.get().getUsername())
                .build();
    }

    @Override
    public User getCurrentUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
