package com.drivesoft.idmsdatafetcher.service.Impl;

import com.drivesoft.idmsdatafetcher.dto.request.LoginRequest;
import com.drivesoft.idmsdatafetcher.dto.response.LoginResponse;
import com.drivesoft.idmsdatafetcher.entity.User;
import com.drivesoft.idmsdatafetcher.exception.CommonException;
import com.drivesoft.idmsdatafetcher.repository.UserRepository;
import com.drivesoft.idmsdatafetcher.service.AuthService;
import com.drivesoft.idmsdatafetcher.utils.AppConstant;
import com.drivesoft.idmsdatafetcher.utils.Encrypter;
import com.drivesoft.idmsdatafetcher.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        try {
            Optional<User> user = userRepository.findByUserName(loginRequest.getUsername());
            LoginResponse loginResponse = LoginResponse.builder().isAuthenticated(false).message(AppConstant.USERNAME_PASSWORD_MISMATCH).build();
            if (user.isPresent()) {
                if (user.get().getPassword().equals(loginRequest.getPassword())) {
                    loginResponse.setAuthenticated(true);
                    loginResponse.setToken(jwtUtils.generateJwtTokenUsingUserName(loginRequest.getUsername()));
                    loginResponse.setMessage(AppConstant.SUCCESS);
                }
            }
            return loginResponse;
        } catch (Exception e) {
            log.error("Error while login a user: {}", e.getMessage());
            throw new CommonException("Failed to authenticate user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
