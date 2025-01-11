package com.drivesoft.idmsdatafetcher.service;

import com.drivesoft.idmsdatafetcher.dto.request.LoginRequest;
import com.drivesoft.idmsdatafetcher.dto.response.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
public LoginResponse authenticate(LoginRequest loginRequest);
}
