package com.hr_system.controller;

import com.hr_system.dto.CreateDepartmentRequest;
import com.hr_system.dto.DepartmentResponse;
import com.hr_system.dto.UpdateDepartmentRequest;
import com.hr_system.service.DepartmentService;
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
@RequestMapping("/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAllDepartments(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<DepartmentResponse> departments = departmentService.getAllDepartments(filterParams);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Departments retrieved successfully", departments)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> searchDepartments(
            @RequestParam(required = false) String keyword) {
        List<DepartmentResponse> departments = departmentService.searchDepartments(keyword);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Departments search completed successfully", departments)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(@PathVariable UUID id) {
        DepartmentResponse department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Department retrieved successfully", department)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request) {
        DepartmentResponse department = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseUtils.createResponse("success", "Department created successfully", department)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDepartmentRequest request) {
        DepartmentResponse department = departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Department updated successfully", department)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable UUID id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Department deleted successfully")
        );
    }
}