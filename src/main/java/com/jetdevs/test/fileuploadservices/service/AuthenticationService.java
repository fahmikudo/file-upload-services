package com.jetdevs.test.fileuploadservices.service;

import com.jetdevs.test.fileuploadservices.entity.User;
import com.jetdevs.test.fileuploadservices.model.AuthRequest;
import com.jetdevs.test.fileuploadservices.model.AuthResponse;

public interface AuthenticationService {

    AuthResponse login(AuthRequest authRequest);

    User getCurrentUser(String username);

}
