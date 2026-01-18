package com.hr_system.controller;

import com.hr_system.requests.CreateEmployeeLeaveRequest;
import com.hr_system.requests.UpdateEmployeeLeaveRequest;
import com.hr_system.responses.EmployeeLeaveResponse;
import com.hr_system.service.EmployeeLeaveService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/employee-leaves")
@RequiredArgsConstructor
public class EmployeeLeaveController {

    private final EmployeeLeaveService employeeLeaveService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<EmployeeLeaveResponse>>> getAllEmployeeLeaves() {
        List<EmployeeLeaveResponse> leaves = employeeLeaveService.getAllEmployeeLeaves();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Employee leaves retrieved successfully", leaves));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeLeaveResponse>> createEmployeeLeave(@Valid @RequestBody CreateEmployeeLeaveRequest request) {
        EmployeeLeaveResponse leave = employeeLeaveService.createEmployeeLeave(request);
        return ResponseEntity.status(201).body(ApiResponseUtils.createResponse("success", "Employee leave created successfully", leave));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeLeaveResponse>> updateEmployeeLeave(@PathVariable UUID id, @Valid @RequestBody UpdateEmployeeLeaveRequest request) {
        EmployeeLeaveResponse leave = employeeLeaveService.updateEmployeeLeave(id, request);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Employee leave updated successfully", leave));
    }
}