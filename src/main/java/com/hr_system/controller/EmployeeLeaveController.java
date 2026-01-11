package com.hr_system.controller;

import com.hr_system.dto.CreateEmployeeLeaveRequest;
import com.hr_system.dto.EmployeeLeaveResponse;
import com.hr_system.dto.UpdateEmployeeLeaveRequest;
import com.hr_system.service.EmployeeLeaveService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/employee-leaves")
@RequiredArgsConstructor
public class EmployeeLeaveController {

    private final EmployeeLeaveService employeeLeaveService;

    @GetMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<EmployeeLeaveResponse>>> getAllEmployeeLeaves(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<EmployeeLeaveResponse> leaves = employeeLeaveService.getAllEmployeeLeaves(filterParams);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee leaves retrieved successfully", leaves)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<EmployeeLeaveResponse>>> searchEmployeeLeaves(
            @RequestParam(required = false) String keyword) {
        List<EmployeeLeaveResponse> leaves = employeeLeaveService.searchEmployeeLeaves(keyword);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee leaves search completed successfully", leaves)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeLeaveResponse>> getEmployeeLeaveById(@PathVariable UUID id) {
        EmployeeLeaveResponse leave = employeeLeaveService.getEmployeeLeaveById(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee leave retrieved successfully", leave)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeLeaveResponse>> createEmployeeLeave(
            @Valid @RequestBody CreateEmployeeLeaveRequest request) {
        EmployeeLeaveResponse leave = employeeLeaveService.createEmployeeLeave(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseUtils.createResponse("success", "Employee leave created successfully", leave)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeLeaveResponse>> updateEmployeeLeave(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEmployeeLeaveRequest request) {
        EmployeeLeaveResponse leave = employeeLeaveService.updateEmployeeLeave(id, request);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee leave updated successfully", leave)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<Void>> deleteEmployeeLeave(@PathVariable UUID id) {
        employeeLeaveService.deleteEmployeeLeave(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee leave deleted successfully")
        );
    }
}