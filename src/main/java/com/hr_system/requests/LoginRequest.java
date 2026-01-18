package com.hr_system.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}