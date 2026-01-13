package com.hr_system.controller;

import com.hr_system.dto.ContractResponse;
import com.hr_system.dto.CreateContractRequest;
import com.hr_system.dto.UpdateContractRequest;
import com.hr_system.service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public ResponseEntity<List<ContractResponse>> getAllContracts(
            @RequestParam(required = false) Map<String, String> filterParams) {
        List<ContractResponse> contracts = contractService.getAllContracts(filterParams);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContractResponse>> searchContracts(
            @RequestParam(required = false) String keyword) {
        List<ContractResponse> contracts = contractService.searchContracts(keyword);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponse> getContractById(@PathVariable UUID id) {
        ContractResponse contract = contractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }

    @PostMapping
    public ResponseEntity<ContractResponse> createContract(
            @Valid @RequestBody CreateContractRequest request) {
        ContractResponse contract = contractService.createContract(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(contract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractResponse> updateContract(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateContractRequest request) {
        ContractResponse contract = contractService.updateContract(id, request);
        return ResponseEntity.ok(contract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable UUID id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}