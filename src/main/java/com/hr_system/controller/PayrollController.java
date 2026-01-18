package com.hr_system.controller;

import com.hr_system.requests.CreatePayrollRequest;
import com.hr_system.requests.UpdatePayrollRequest;
import com.hr_system.responses.PayrollResponse;
import com.hr_system.service.PayrollService;
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
@RequestMapping("/v1/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<PayrollResponse>>> getAllPayrolls() {
        List<PayrollResponse> payrolls = payrollService.getAllPayrolls();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Payrolls retrieved successfully", payrolls));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PayrollResponse>> createPayroll(@Valid @RequestBody CreatePayrollRequest request) {
        PayrollResponse payroll = payrollService.createPayroll(request);
        return ResponseEntity.status(201).body(ApiResponseUtils.createResponse("success", "Payroll created successfully", payroll));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PayrollResponse>> updatePayroll(@PathVariable UUID id, @Valid @RequestBody UpdatePayrollRequest request) {
        PayrollResponse payroll = payrollService.updatePayroll(id, request);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Payroll updated successfully", payroll));
    }
}