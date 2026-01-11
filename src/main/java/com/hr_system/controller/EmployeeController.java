package com.hr_system.controller;

import com.hr_system.dto.CreateEmployeeRequest;
import com.hr_system.dto.EmployeeResponse;
import com.hr_system.dto.UpdateEmployeeRequest;
import com.hr_system.service.EmployeeService;
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
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<EmployeeResponse> employees = employeeService.getAllEmployees(filterParams);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employees retrieved successfully", employees)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> searchEmployees(
            @RequestParam(required = false) String keyword) {
        List<EmployeeResponse> employees = employeeService.searchEmployees(keyword);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employees search completed successfully", employees)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable UUID id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee retrieved successfully", employee)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest request) {
        EmployeeResponse employee = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseUtils.createResponse("success", "Employee created successfully", employee)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse employee = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee updated successfully", employee)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee deleted successfully")
        );
    }
}