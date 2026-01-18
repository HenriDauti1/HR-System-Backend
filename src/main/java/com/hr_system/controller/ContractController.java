package com.hr_system.controller;

import com.hr_system.requests.CreateContractRequest;
import com.hr_system.requests.UpdateContractRequest;
import com.hr_system.responses.ContractResponse;
import com.hr_system.service.ContractService;
import com.hr_system.util.ApiResponse;
import com.hr_system.util.ApiResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getAllContracts() {
        List<ContractResponse> contracts = contractService.getAllContracts();
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Contracts retrieved successfully", contracts));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContractResponse>> createContract(@Valid @RequestBody CreateContractRequest request) {
        ContractResponse contract = contractService.createContract(request);
        return ResponseEntity.status(201).body(ApiResponseUtils.createResponse("success", "Contract created successfully", contract));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContractResponse>> updateContract(@PathVariable UUID id, @Valid @RequestBody UpdateContractRequest request) {
        ContractResponse contract = contractService.updateContract(id, request);
        return ResponseEntity.status(200).body(ApiResponseUtils.createResponse("success", "Contract updated successfully", contract));
    }
}