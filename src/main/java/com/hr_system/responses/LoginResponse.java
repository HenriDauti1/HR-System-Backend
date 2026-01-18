package com.hr_system.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UUID employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}