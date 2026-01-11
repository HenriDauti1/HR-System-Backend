package com.hr_system.service;

import com.hr_system.dto.LoginRequest;
import com.hr_system.dto.LoginResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String ADMIN_EMAIL = "admin@hrms.com";
    private static final String ADMIN_PASSWORD = "admin123";

    private static final String COORDINATOR_EMAIL = "coordinator@hrms.com";
    private static final String COORDINATOR_PASSWORD = "coordinator123";

    public LoginResponse login(LoginRequest request, HttpSession session) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("userRole", "CRUD");
            return new LoginResponse("CRUD");
        }

        if (COORDINATOR_EMAIL.equals(email) && COORDINATOR_PASSWORD.equals(password)) {
            session.setAttribute("userRole", "READONLY");
            return new LoginResponse("READONLY");
        }

        throw new RuntimeException("Invalid email or password");
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}