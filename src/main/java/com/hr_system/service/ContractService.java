package com.hr_system.service;

import com.hr_system.entity.Contract;
import com.hr_system.entity.Employee;
import com.hr_system.exception.ResourceNotFoundException;
import com.hr_system.repository.ContractRepository;
import com.hr_system.repository.EmployeeRepository;
import com.hr_system.requests.CreateContractRequest;
import com.hr_system.requests.UpdateContractRequest;
import com.hr_system.responses.ContractResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<ContractResponse> getAllContracts() {
        List<Contract> contracts = contractRepository.findAllByEmployee_IsActiveTrue();
        return contracts.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Transactional
    public ContractResponse createContract(CreateContractRequest request) {
        Employee employee = employeeRepository.findByEmployeeIdAndIsActiveTrue(request.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (request.getEndDate() != null && !request.getEndDate().isAfter(request.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setContractType(request.getContractType());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setSalary(request.getSalary());

        Contract savedContract = contractRepository.save(contract);
        return convertToResponse(savedContract);
    }

    @Transactional
    public ContractResponse updateContract(UUID id, UpdateContractRequest request) {
        Contract contract = contractRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));

        Employee employee = employeeRepository.findByEmployeeIdAndIsActiveTrue(request.getEmployeeId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        if (request.getEndDate() != null && !request.getEndDate().isAfter(request.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        contract.setEmployee(employee);
        contract.setContractType(request.getContractType());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setSalary(request.getSalary());

        Contract updatedContract = contractRepository.save(contract);
        return convertToResponse(updatedContract);
    }

    private ContractResponse convertToResponse(Contract contract) {
        ContractResponse response = new ContractResponse();
        response.setContractId(contract.getContractId());
        response.setEmployeeId(contract.getEmployee().getEmployeeId());

        Employee emp = contract.getEmployee();
        String fullName = emp.getFirstName() + " " + emp.getLastName();
        response.setEmployeeName(fullName);

        response.setContractType(contract.getContractType());
        response.setStartDate(contract.getStartDate());
        response.setEndDate(contract.getEndDate());
        response.setSalary(contract.getSalary());
        response.setCreatedAt(contract.getCreatedAt());
        response.setUpdatedAt(contract.getUpdatedAt());
        return response;
    }
}