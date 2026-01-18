package com.hr_system.service;

import com.hr_system.requests.LoginRequest;
import com.hr_system.responses.LoginResponse;
import com.hr_system.entity.User;
import com.hr_system.repository.LoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(LoginRepository loginRepository, PasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request, HttpSession session) {

        User user = loginRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        session.setAttribute("userRole", user.getRole());
        return new LoginResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getRole());
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
