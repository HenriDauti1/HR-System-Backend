package com.hr_system.controller;

import com.hr_system.dto.CreatePayrollRequest;
import com.hr_system.dto.PayrollResponse;
import com.hr_system.dto.UpdatePayrollRequest;
import com.hr_system.service.PayrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @GetMapping
    public ResponseEntity<List<PayrollResponse>> getAllPayrolls(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<PayrollResponse> payrolls = payrollService.getAllPayrolls(filterParams);
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PayrollResponse>> searchPayrolls(
            @RequestParam(required = false) String keyword) {
        List<PayrollResponse> payrolls = payrollService.searchPayrolls(keyword);
        return ResponseEntity.ok(payrolls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollResponse> getPayrollById(@PathVariable UUID id) {
        PayrollResponse payroll = payrollService.getPayrollById(id);
        return ResponseEntity.ok(payroll);
    }

    @PostMapping
    public ResponseEntity<PayrollResponse> createPayroll(
            @Valid @RequestBody CreatePayrollRequest request) {
        PayrollResponse payroll = payrollService.createPayroll(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(payroll);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollResponse> updatePayroll(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePayrollRequest request) {
        PayrollResponse payroll = payrollService.updatePayroll(id, request);
        return ResponseEntity.ok(payroll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable UUID id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.noContent().build();
    }
}