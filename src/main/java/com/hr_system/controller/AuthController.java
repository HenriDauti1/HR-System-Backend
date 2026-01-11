package com.hr_system.controller;

import com.hr_system.dto.LoginRequest;
import com.hr_system.dto.LoginResponse;
import com.hr_system.service.AuthService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request,
            HttpSession session) {
        LoginResponse response = authService.login(request, session);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Login successful", response)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Logged out successfully")
        );
    }
}