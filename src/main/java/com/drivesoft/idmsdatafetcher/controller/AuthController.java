package com.drivesoft.idmsdatafetcher.controller;

import com.drivesoft.idmsdatafetcher.dto.request.LoginRequest;
import com.drivesoft.idmsdatafetcher.dto.response.LoginResponse;
import com.drivesoft.idmsdatafetcher.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/user")
@Tag(name = "User API", description = "Operations related to User Authentication")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(
            summary = "For user login",
            description = "Retrieve a jwt bearer token used for protected routes")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Login request: {}", loginRequest.toString());
        return ResponseEntity.ok().body(authService.authenticate(loginRequest));
    }
}
