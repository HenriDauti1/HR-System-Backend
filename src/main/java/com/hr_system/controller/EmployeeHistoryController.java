package com.hr_system.controller;

import com.hr_system.requests.CreateEmployeeHistoryRequest;
import com.hr_system.requests.UpdateEmployeeHistoryRequest;
import com.hr_system.responses.EmployeeHistoryResponse;
import com.hr_system.service.EmployeeHistoryService;
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
@RequestMapping("/v1/employee-histories")
@RequiredArgsConstructor
public class EmployeeHistoryController {

    private final EmployeeHistoryService employeeHistoryService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<EmployeeHistoryResponse>>> getAllEmployeeHistories() {
        List<EmployeeHistoryResponse> histories = employeeHistoryService.getAllEmployeeHistories();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Employee histories retrieved successfully", histories));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeHistoryResponse>> createEmployeeHistory(@Valid @RequestBody CreateEmployeeHistoryRequest request) {
        EmployeeHistoryResponse history = employeeHistoryService.createEmployeeHistory(request);
        return ResponseEntity.status(201).body(ApiResponseUtils.createResponse("success", "Employee history created successfully", history));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeHistoryResponse>> updateEmployeeHistory(@PathVariable UUID id, @Valid @RequestBody UpdateEmployeeHistoryRequest request) {
        EmployeeHistoryResponse history = employeeHistoryService.updateEmployeeHistory(id, request);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Employee history updated successfully", history));
    }
}