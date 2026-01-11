package com.hr_system.controller;

import com.hr_system.dto.CreateEmployeeHistoryRequest;
import com.hr_system.dto.EmployeeHistoryResponse;
import com.hr_system.dto.UpdateEmployeeHistoryRequest;
import com.hr_system.service.EmployeeHistoryService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/employee-histories")
@RequiredArgsConstructor
public class EmployeeHistoryController {

    private final EmployeeHistoryService employeeHistoryService;

    @GetMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<EmployeeHistoryResponse>>> getAllEmployeeHistories(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<EmployeeHistoryResponse> histories = employeeHistoryService.getAllEmployeeHistories(filterParams);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee histories retrieved successfully", histories)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<EmployeeHistoryResponse>>> searchEmployeeHistories(
            @RequestParam(required = false) String keyword) {
        List<EmployeeHistoryResponse> histories = employeeHistoryService.searchEmployeeHistories(keyword);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee histories search completed successfully", histories)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeHistoryResponse>> getEmployeeHistoryById(@PathVariable UUID id) {
        EmployeeHistoryResponse history = employeeHistoryService.getEmployeeHistoryById(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee history retrieved successfully", history)
        );
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<List<EmployeeHistoryResponse>>> getEmployeeHistoriesByEmployeeId(
            @PathVariable UUID employeeId) {
        List<EmployeeHistoryResponse> histories = employeeHistoryService.getEmployeeHistoriesByEmployeeId(employeeId);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee histories retrieved successfully", histories)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeHistoryResponse>> createEmployeeHistory(
            @Valid @RequestBody CreateEmployeeHistoryRequest request) {
        EmployeeHistoryResponse history = employeeHistoryService.createEmployeeHistory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseUtils.createResponse("success", "Employee history created successfully", history)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeHistoryResponse>> updateEmployeeHistory(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEmployeeHistoryRequest request) {
        EmployeeHistoryResponse history = employeeHistoryService.updateEmployeeHistory(id, request);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee history updated successfully", history)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<Void>> deleteEmployeeHistory(@PathVariable UUID id) {
        employeeHistoryService.deleteEmployeeHistory(id);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee history deleted successfully")
        );
    }

    @PatchMapping("/{id}/end")
    @PreAuthorize("hasRole('CRUD')")
    public ResponseEntity<ApiResponse<EmployeeHistoryResponse>> endEmployeeHistory(
            @PathVariable UUID id,
            @RequestParam OffsetDateTime endDate) {
        EmployeeHistoryResponse history = employeeHistoryService.endEmployeeHistory(id, endDate);
        return ResponseEntity.ok(
                ApiResponseUtils.createResponse("success", "Employee history ended successfully", history)
        );
    }
}
